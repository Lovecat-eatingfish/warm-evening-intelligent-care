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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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


    /**
     * 生成token====直接生成token
     * 因为jwt生成的token 过期时间是固定的 用redis来实现
     *
     * @param
     * @return
     */
    public <T> String createToken(T t) {
        UserLogin userLogin = new UserLogin();
        if (t instanceof UserLogin) {
            userLogin = (UserLogin) t;
        }
        userLogin.setLoginTime(System.currentTimeMillis());
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userLogin.getId());
        // 吧用户信息放入到redis中
        refresh(userLogin);
        return Jwts.builder()
                .setSubject("MEMBER_LOGIN_USER")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 解析token  且刷新token
     *
     * @param token
     * @return
     */
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
            // 用用户的id 作为redis的key 可以实现 剔除登录的用户的功能
            UserLogin user = (UserLogin) redisUtil.get(RedisConstant.USER_LOGIN_CACHE + userId);
            if (user == null) {
                throw new ServiceException(ResultCodeEnum.LOGIN_FAILED);
            }
            // 检测是不是 要刷新token
            Long loginTime = user.getLoginTime();
            long currentTimeMillis = System.currentTimeMillis();
            long millis = currentTimeMillis / 1000 / 60 - loginTime / 1000 / 60;
            if (millis >= 20) {
                user.setLoginTime(currentTimeMillis);
                refresh(user);
            }
            // 把用户信息存入到ThreadLocal中
            LoginUserHolder.setLoginUser(user);
            return body;
        } catch (ExpiredJwtException e) {
            throw new ServiceException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new ServiceException(ResultCodeEnum.TOKEN_INVALID);
        }
    }

    /**
     * 刷新token的条件
     */
    public <T> void refresh(T t) {
        UserLogin userLogin = null;
        if (t instanceof UserLogin) {
            userLogin = (UserLogin) t;
            redisUtil.set(RedisConstant.USER_LOGIN_CACHE + userLogin.getId(), userLogin, RedisConstant.EXPIRE_TIME, TimeUnit.MINUTES);
        }
    }
}
