package com.innovation.warm.controller;

import com.innovation.warm.domain.dto.UserLoginDTO;
import com.innovation.warm.pojo.entity.UserLogin;
import com.innovation.warm.response.Result;
import com.innovation.warm.service.UserLoginService;
import com.innovation.warm.util.LoginUserHolder;
import com.innovation.warm.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: UserLoginController
 * PackageName: com.innovation.warm.controller
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/13 下午11:06
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/member/user")
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLogin) {
        log.info("微信用户的code是：{}", userLogin.getCode());
        String token = userLoginService.login(userLogin);
        log.info("响应的token是：{}", token);
        return Result.success(token);
    }
    @GetMapping("/info")
    public Result getUserInfo() {
        UserLogin loginUser = LoginUserHolder.getLoginUser();
        return Result.success(loginUser);
    }

    // TODO 注意修改用户信息的时候  修改redis的信息 应为ThreadLocal的信息使用redis中取出来的
    @PutMapping("/info")
    public Result updateUserInfo(@RequestBody UserLoginDTO userLoginDTO) {
        userLoginService.updateUserInfo(userLoginDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getUserInfo(@PathVariable("id") Long id) {
        UserLogin userLogin = userLoginService.getById(id);
        return  Result.success(userLogin);
    }
}
