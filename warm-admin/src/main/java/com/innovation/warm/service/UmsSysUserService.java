package com.innovation.warm.service;

import com.innovation.warm.domain.dto.SysUserLoginDTO;
import com.innovation.warm.pojo.entity.UmsSysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 32782
* @description 针对表【ums_sys_user(后台用户表)】的数据库操作Service
* @createDate 2024-11-14 22:52:52
*/
public interface UmsSysUserService extends IService<UmsSysUser> {

    String login(SysUserLoginDTO userLoginDTO);
}
