package com.innovation.warm.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovation.warm.pojo.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台用户表
 *
 * @author 32782
 * @TableName ums_sys_user
 */
@TableName(value = "ums_sys_user")
@Data
public class UmsSysUser implements Serializable {
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


    // 角色信息
    @TableField(exist = false)
    private List<UmsRole> roleList = new ArrayList<>();

    @TableField(exist = false)
    private List<String> perms = new ArrayList<>();
}