package com.innovation.warm.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovation.warm.pojo.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName ums_menu
 */
@TableName(value ="ums_menu")
@Data
public class UmsMenu  implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型：0，目录，1菜单，2：按钮
     */
    private Integer menuType;

    /**
     * 路由路径   system/user/index
     */
    private String path;

    /**
     * 组件路径  如：sysUser
     */
    private String componentPath;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态：0：可用，1：不可用
     */
    private Integer status;

    private String creatorId;
    private String updaterId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    // 更新字段 再添加和修改的时候都会区 更新数据
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}