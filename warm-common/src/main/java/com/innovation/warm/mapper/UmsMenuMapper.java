package com.innovation.warm.mapper;

import com.innovation.warm.pojo.entity.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
* @author 32782
* @description 针对表【ums_menu】的数据库操作Mapper
* @createDate 2024-11-16 16:25:34
* @Entity com.innovation.warm.pojo.entity.UmsMenu
*/
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    Set<UmsMenu> getMenusByRoleIds(@Param("roleIds") List<Long> roleIds);
}




