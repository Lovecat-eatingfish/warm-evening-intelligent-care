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

    private String code;

}
