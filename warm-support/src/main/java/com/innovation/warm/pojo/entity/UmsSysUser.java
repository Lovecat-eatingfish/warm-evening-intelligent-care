package com.innovation.warm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.innovation.warm.domain.UmsRole;
import com.innovation.warm.pojo.base.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台用户表
 * @TableName ums_sys_user
 */
@TableName(value ="ums_sys_user")
@Data
public class UmsSysUser extends BaseEntity {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    // 角色信息
    @TableField(exist = false)
    private List<UmsRole> roleList = new ArrayList<>();

    @TableField(exist = false)
    private List<String> perms = new ArrayList<>();
}