package com.innovation.warm.controller;

import com.innovation.warm.domain.dto.UserLoginDTO;
import com.innovation.warm.pojo.entity.UserLogin;
import com.innovation.warm.response.Result;
import com.innovation.warm.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLogin) {
        log.info("微信用户的code是：{}", userLogin.getCode());
        String token = userLoginService.login(userLogin);
        log.info("响应的token是：{}", token);
        return Result.success(token);
    }
}
