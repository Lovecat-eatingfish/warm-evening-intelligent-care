package com.innovation.warm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.innovation.warm.domain.dto.SysUserLoginDTO;
import com.innovation.warm.pojo.entity.UmsSysUser;
import com.innovation.warm.service.UmsSysUserService;
import com.innovation.warm.mapper.UmsSysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 32782
* @description 针对表【ums_sys_user(后台用户表)】的数据库操作Service实现
* @createDate 2024-11-14 22:52:52
*/
@Service
public class UmsSysUserServiceImpl extends ServiceImpl<UmsSysUserMapper, UmsSysUser>
    implements UmsSysUserService{

    @Override
    public String login(SysUserLoginDTO userLoginDTO) {
        return "";
    }
}




