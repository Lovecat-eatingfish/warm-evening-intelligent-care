package com.innovation.warm.util.jwt;

import com.innovation.warm.constant.Constants;
import com.innovation.warm.constant.RedisConstant;
import com.innovation.warm.enumeration.ResultCodeEnum;
import com.innovation.warm.exception.ServiceException;
import com.innovation.warm.pojo.dto.SysUserDetails;
import com.innovation.warm.pojo.entity.UmsSysUser;
import com.innovation.warm.properties.JwtProperties;
import com.innovation.warm.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SystemUserTokenUtil
 * PackageName: com.innovation.warm.util.jwt
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/16 下午7:24
 * @Version: 1.0
 */
// 给后台管理系统提供的jwt工具类
// 可以把用户的信息存放到 redis中 再拦截器哪里解析出阿里放到security holder里面  或者把用户的信息存储放到jwt中机密 经过蓝机器 解析出来存放到holder中
// 为了实现剔除下线功能 还是用用户的id 作为redis的key
@Component
@Slf4j
public class SystemUserTokenUtil {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisUtil redisUtil;

    private final static String SYSUSERID = "sysUserId";


    public String createToken(SysUserDetails sysUserDetails) {
        // 设置登录时间 或者说也可叫成 上一次发送请求的时间  只要间隔超过 20 分钟自动刷新redis的数据  只要超过30分钟
        // 没有发送请求就会redis数据过期 用户重新登录
        sysUserDetails.setLoginTime(System.currentTimeMillis());

        // 把用户信息存放到redis中  这个放在上一行设置上一次确切时间代码后面
        redisUtil.set(RedisConstant.SYSUSER_LOGIN_CACHE + sysUserDetails.getSysUserId(), sysUserDetails, RedisConstant.EXPIRE_TIME, TimeUnit.MINUTES);
        Map<String, Object> claims = new HashMap<>();
        claims.put(SYSUSERID, sysUserDetails.getSysUserId());
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        String secret = jwtProperties.getSecret();
        log.info("密钥时： {}" , jwtProperties.getSecret());
        if (secret == null || secret.isEmpty()) {
            log.error("JWT secret is not configured correctly");
            throw new IllegalArgumentException("JWT secret is not configured correctly");
        }
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    public SysUserDetails getSysLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            Long sysUserId = claims.get(SYSUSERID, Long.class);
            SysUserDetails sysUserDetails = (SysUserDetails) redisUtil.get(RedisConstant.SYSUSER_LOGIN_CACHE + sysUserId);
            //  作用：1 踢人下线功能  2： 充当jwt的 过期时间 =====》 解决jwt 的过期时间 定死的问题 无法强制用户下线的问题
            if (sysUserDetails == null) {
                throw new ServiceException(ResultCodeEnum.LOGIN_FAILED);
            }
            // 如果以切正常：
//            因为这个时 再拦截器里面 每一个请求都要走 再这里刷新token
            verifyToken(sysUserDetails);
        }
        return null;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeadTokenKey());
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_SYS_PREFIX)) {
            token = token.replace(Constants.TOKEN_SYS_PREFIX, "");
        }
        return token;
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }


    // 刷新token  但是不用经常刷新token
    public void verifyToken(SysUserDetails sysUserDetails) {
        Long loginTime = sysUserDetails.getLoginTime();
        long currentTimeMillis = System.currentTimeMillis();

        if (currentTimeMillis - loginTime >= 25 * 60 * 1000) {
            refreshToken(sysUserDetails);
        }

    }

    private void refreshToken(SysUserDetails sysUserDetails) {
        sysUserDetails.setLoginTime(System.currentTimeMillis());
        redisUtil.set(RedisConstant.SYSUSER_LOGIN_CACHE + sysUserDetails.getSysUserId(), sysUserDetails, RedisConstant.EXPIRE_TIME, TimeUnit.MINUTES);
    }

}
