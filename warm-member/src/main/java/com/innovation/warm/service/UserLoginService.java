package com.innovation.warm.service;

import com.innovation.warm.domain.dto.UserLoginDTO;
import com.innovation.warm.pojo.entity.UserLogin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 32782
* @description 针对表【user_login(用户登录)】的数据库操作Service
* @createDate 2024-11-13 22:43:58
*/
public interface UserLoginService extends IService<UserLogin> {

    String login(UserLoginDTO userLoginDTO);

}
