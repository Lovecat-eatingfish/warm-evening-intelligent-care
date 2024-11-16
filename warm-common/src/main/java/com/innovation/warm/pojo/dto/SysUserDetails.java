package com.innovation.warm.pojo.dto;

import cn.hutool.core.util.ObjectUtil;

import com.innovation.warm.pojo.entity.UmsSysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: SysUserDetails
 * PackageName: com.innovation.warm.pojo.dto
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/14 下午11:08
 * @Version: 1.0
 */
@Data
public class SysUserDetails implements UserDetails {
    // 用户的id
    private Long sysUserId;
    private Long loginTime;
    private UmsSysUser sysUser = new UmsSysUser();

    public SysUserDetails() {

    }
    public SysUserDetails(UmsSysUser sysUser){
        this.sysUserId = sysUser.getId();
        this.sysUser = sysUser;
    }
    /**
     * 用户的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> perms = sysUser.getPerms();
        // 判空，返回数据
        if(ObjectUtil.isNotEmpty(perms)) {
            return perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        // 返回一个空集合
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getStatus() == 0;
    }
}
