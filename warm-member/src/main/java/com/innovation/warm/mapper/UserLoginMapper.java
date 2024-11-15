package com.innovation.warm.mapper;

import com.innovation.warm.domain.dto.UserLoginDTO;
import com.innovation.warm.pojo.entity.UserLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 32782
* @description 针对表【user_login(用户登录)】的数据库操作Mapper
* @createDate 2024-11-13 22:43:58
* @Entity com.innovation.warm.pojo.entity.UserLogin
*/
public interface UserLoginMapper extends BaseMapper<UserLogin> {

    void updateUserInfo(@Param("userLoginDTO") UserLoginDTO userLoginDTO);
}




