package com.innovation.warm.util;

import com.innovation.warm.exception.ServiceException;
import com.innovation.warm.pojo.entity.UmsSysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * ClassName: SecurityUtils
 * PackageName: com.innovation.warm.util
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/16 下午4:58
 * @Version: 1.0
 */
@Slf4j
public class SecurityUtils {
    public static SecurityContext getSecurityHolder() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            log.error("获取 SecurityContext 失败");
            throw new ServiceException("获取 SecurityContext 失败");
        }
        return context;
    }

    public static Optional<UmsSysUser> getSysUser() {
        Authentication authentication = getSecurityHolder().getAuthentication();
        if (authentication != null) {
            Object principal =  authentication.getPrincipal();
            if (principal instanceof UmsSysUser) {
                return Optional.of((UmsSysUser)principal);
            }
        }
        return Optional.empty();
    }

    public static Long getSysUserId() {
        return getSysUser().map(UmsSysUser::getId).orElse(null);
    }
}
