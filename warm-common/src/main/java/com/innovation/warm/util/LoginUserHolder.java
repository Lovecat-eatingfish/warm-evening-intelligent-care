package com.innovation.warm.util;

import com.innovation.warm.pojo.entity.UserLogin;

/**
 * ClassName: LoginUserHolder
 * PackageName: com.innovation.warm.util
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/13 下午10:33
 * @Version: 1.0
 */
public class LoginUserHolder {
    private static final ThreadLocal<UserLogin> threadLocal = new ThreadLocal<>();

    public static UserLogin getLoginUser() {
        return threadLocal.get();
    }

    public static void setLoginUser(UserLogin user) {
        threadLocal.set(user);
    }

    public static void removeUser() {
        threadLocal.remove();
    }
}
