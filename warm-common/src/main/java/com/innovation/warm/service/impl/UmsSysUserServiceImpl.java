package com.innovation.warm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.innovation.warm.pojo.dto.SysUserDetails;
import com.innovation.warm.pojo.dto.SysUserLoginDTO;
import com.innovation.warm.pojo.entity.UmsSysUser;
import com.innovation.warm.service.UmsSysUserService;
import com.innovation.warm.mapper.UmsSysUserMapper;
import com.innovation.warm.util.SecurityUtils;
import com.innovation.warm.util.jwt.SystemUserTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
* @author 32782
* @description 针对表【ums_sys_user(后台用户表)】的数据库操作Service实现
* @createDate 2024-11-14 22:52:52
*/
@Service
@Slf4j
public class UmsSysUserServiceImpl extends ServiceImpl<UmsSysUserMapper, UmsSysUser>
    implements UmsSysUserService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SystemUserTokenUtil systemUserTokenUtil;

    @Override
    public String login(SysUserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.error("登录失败 用户名或者密码错误");
            throw new AuthenticationServiceException("登录失败 用户名或者密码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken1 = new UsernamePasswordAuthenticationToken(authentication, null, authentication.getAuthorities());
        SecurityUtils.getSecurityHolder().setAuthentication(authenticationToken1);
        // 生成token 如果要想 实现 替人下线功能  要用redis 作为 中间的条件  且 key要标识 唯一的 SysUser
        SysUserDetails principal = (SysUserDetails) authentication.getPrincipal();
        return systemUserTokenUtil.createToken(principal);
    }
}




