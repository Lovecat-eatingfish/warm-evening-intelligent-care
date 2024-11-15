package com.innovation.warm.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
public class SysUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
