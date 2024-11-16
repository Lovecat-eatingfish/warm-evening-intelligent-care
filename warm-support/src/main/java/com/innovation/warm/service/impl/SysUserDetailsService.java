package com.innovation.warm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.innovation.warm.mapper.UmsMenuMapper;
import com.innovation.warm.mapper.UmsRoleMapper;
import com.innovation.warm.mapper.UmsSysUserMapper;
import com.innovation.warm.pojo.dto.SysUserDetails;
import com.innovation.warm.pojo.entity.UmsMenu;
import com.innovation.warm.pojo.entity.UmsRole;
import com.innovation.warm.pojo.entity.UmsSysUser;
import com.innovation.warm.service.UmsMenuService;
import com.innovation.warm.service.UmsRoleService;
import com.innovation.warm.service.UmsSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * ClassName: SysUserDetailsService
 * PackageName: com.innovation.warm.service.impl
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/14 下午11:03
 * @Version: 1.0
 */
@Service
@Slf4j
public class SysUserDetailsService implements UserDetailsService {
    @Autowired
    private UmsSysUserMapper sysUserMapper;
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UmsSysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<UmsSysUser>().eq(UmsSysUser::getUsername, username));
        if (sysUser == null) {
            log.info("{}这个用户名找不懂", username);
            throw new UsernameNotFoundException(username);
        }

        // 根据用户id 来查找用户的 权限等信息  五表联查太慢了
        // 现根据用户 角色id查询 角色信息
        List<UmsRole> roleList = roleMapper.getRolesById(sysUser.getId());
        if (CollectionUtil.isNotEmpty(roleList)) {
            List<Long> roleIds = roleList.stream().map(UmsRole::getRoleId).toList();
            Set<UmsMenu> menuSet = menuMapper.getMenusByRoleIds(roleIds);
            sysUser.setRoleList(roleList);
            sysUser.setPerms(menuSet.stream().map(UmsMenu::getPerms).toList());
        }
        // 根据角色信息查询权限信息
        return new SysUserDetails(sysUser);
    }
}
