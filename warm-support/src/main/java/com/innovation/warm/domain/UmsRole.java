package com.innovation.warm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.innovation.warm.pojo.base.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName ums_role
 */
@TableName(value ="ums_role")
@Data
public class UmsRole extends BaseEntity implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}