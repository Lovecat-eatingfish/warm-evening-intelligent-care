package com.innovation.warm.util;

import com.innovation.warm.constant.RedisConstant;
import com.innovation.warm.enumeration.ResultCodeEnum;
import com.innovation.warm.exception.ServiceException;
import com.innovation.warm.pojo.entity.UserLogin;
import com.innovation.warm.properties.JwtProperties;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: JwtUtil
 * PackageName: com.innovation.warm.util
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/14 下午5:30
 * @Version: 1.0
 */
@Component
public class JwtUtil {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisUtil redisUtil;
    public String createToken(UserLogin userLogin) {
        return Jwts.builder().setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpireTime()))
                .setSubject("LOGIN_USER")
                .claim("userId", userLogin.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }
    public Claims parseToken(String token) {
        if (token == null) {
            throw new ServiceException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }
        try {
            JwtParser jwtParser = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret());
            Jws<Claims> headerClaimsJwt = jwtParser.parseClaimsJws(token);
            Claims body = headerClaimsJwt.getBody();
            Long userId = body.get("userId", Long.class);
            UserLogin user = (UserLogin) redisUtil.get(RedisConstant.USER_LOGIN_CACHE + userId);
            LoginUserHolder.setLoginUser(user);
            return body;
        } catch (ExpiredJwtException e) {
            throw new ServiceException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new ServiceException(ResultCodeEnum.TOKEN_INVALID);
        }
    }
}
