package com.innovation.warm.constant;

/**
 * ClassName: RedisConstant
 * PackageName: com.innovation.warm.constant
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/14 下午5:54
 * @Version: 1.0
 */
// 小程序端的redis key前缀是 member_user
public class RedisConstant {
    // 小程序用户登录 的redis数据缓存的key前缀
    public static final String USER_LOGIN_CACHE = "member_user:login_cache";
    public static final Integer EXPIRE_TIME = 30;
}
