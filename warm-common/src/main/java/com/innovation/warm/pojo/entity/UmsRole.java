package com.innovation.warm.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovation.warm.pojo.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName ums_role
 */
@TableName(value ="ums_role")
@Data
public class UmsRole  implements Serializable {
    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色标识
     */
    private String roleLabel;

    /**
     * 角色名字
     */
    private String roleName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态：0：可用，1：不可用
     */
    private Integer status;

    /**
     * 是否删除：0: 未删除，1：已删除
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    private String creatorId;
    private String updaterId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    // 更新字段 再添加和修改的时候都会区 更新数据
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}