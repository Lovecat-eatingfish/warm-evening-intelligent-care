package com.innovation.warm.domain.dto;

import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 * @author 32782
 */
@Data
public class UserLoginDTO implements Serializable {

    private Long id;

    private String code;

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


}
