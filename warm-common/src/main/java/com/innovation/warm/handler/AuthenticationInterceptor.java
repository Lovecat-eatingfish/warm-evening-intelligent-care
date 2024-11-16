package com.innovation.warm.handler;

import com.innovation.warm.enumeration.ResultCodeEnum;
import com.innovation.warm.exception.ServiceException;
import com.innovation.warm.properties.JwtProperties;
import com.innovation.warm.util.jwt.JwtMemberUtil;
import com.innovation.warm.util.LoginUserHolder;
import com.innovation.warm.util.ServletUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName: AuthenticationInterceptor
 * PackageName: com.innovation.warm.handler
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/14 下午6:00
 * @Version: 1.0
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtMemberUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = ServletUtil.getToken(jwtProperties.getHeadTokenKey());
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }
        Claims claims = jwtUtil.parseToken(token);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LoginUserHolder.removeUser();
    }
}
