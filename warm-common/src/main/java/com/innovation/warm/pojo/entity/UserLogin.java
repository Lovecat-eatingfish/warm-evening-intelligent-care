package com.innovation.warm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.innovation.warm.pojo.base.BaseEntity;
import lombok.Data;


/**
 * 用户登录
 * @author 32782
 * @TableName user_login
 */
@TableName(value ="user_login")
@Data
public class UserLogin extends BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信用户唯一标识
     */
    private String openid;

    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别((1-男 2-女 0-保密)
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 状态((1-正常 0-禁用)
     */
    private Integer status;

    /**
     * 逻辑删除标志，0: 未删除, 1: 已删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}