package com.innovation.warm.util;

/**
 * ClassName: LoginUserHolder
 * PackageName: com.innovation.warm.util
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/13 下午10:33
 * @Version: 1.0
 */
public class LoginUserHolder<T> {
    private final ThreadLocal<T> threadLocal = new ThreadLocal<>();

    public T getLoginUser() {
        return threadLocal.get();
    }
    public void setLoginUser(T user) {
        threadLocal.set(user);
    }

    public void removeUser() {
        threadLocal.remove();
    }
}
