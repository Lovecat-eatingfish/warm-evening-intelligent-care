package com.innovation.warm.mapper;

import com.innovation.warm.pojo.entity.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 32782
* @description 针对表【ums_role】的数据库操作Mapper
* @createDate 2024-11-14 23:15:28
* @Entity com.innovation.warm.pojo.entity.UmsRole
*/
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 根据 用户id拆线呢 角信息
     * @param sysUserId
     * @return
     */
    List<UmsRole> getRolesById(@Param("sysUserId") Long sysUserId);
}




