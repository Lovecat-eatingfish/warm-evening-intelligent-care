package com.innovation.warm.controller;

import com.innovation.warm.pojo.dto.SysUserLoginDTO;
import com.innovation.warm.response.Result;
import com.innovation.warm.service.UmsSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: UmsSysController
 * PackageName: com.innovation.warm.controller
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/14 下午10:55
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class UmsSysController {
    @Autowired
    private UmsSysUserService sysUserService;

    @PostMapping("/login")
    public Result login(@RequestBody SysUserLoginDTO userLoginDTO) {
     String token = sysUserService.login(userLoginDTO);
     return Result.success(token);
    }
}
