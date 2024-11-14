package com.innovation.warm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.innovation.warm.domain.dto.UserLoginDTO;
import com.innovation.warm.enumeration.ResultCodeEnum;
import com.innovation.warm.exception.ServiceException;
import com.innovation.warm.pojo.entity.UserLogin;
import com.innovation.warm.properties.WeChatProperties;
import com.innovation.warm.service.UserLoginService;
import com.innovation.warm.mapper.UserLoginMapper;
import com.innovation.warm.util.HttpClientUtil;
import com.innovation.warm.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
* @author 32782
* @description 针对表【user_login(用户登录)】的数据库操作Service实现
* @createDate 2024-11-13 22:43:58
*/
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin>
    implements UserLoginService{
    @Autowired
    private JwtUtil jwtUtil;

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private WeChatProperties weChatProperties;

    @Override
    public String login( UserLoginDTO userLoginDTO) {
        String openId = getOpenId(userLoginDTO.getCode());
        if (StringUtils.isEmpty(openId)) {
            throw new ServiceException(ResultCodeEnum.LOGIN_FAILED);
        }

        UserLogin one = this.getOne(new LambdaQueryWrapper<UserLogin>().eq(UserLogin::getOpenid, openId));
        // 该用户没有注册
        if (one == null) {
            one = new UserLogin();
            one.setOpenid(openId);
            this.save(one);
            one.setCreatorId(one.getId());
            one.setUpdaterId(one.getId());
        }
        // 该用户注册过了  生成token返回
        return null;

    }

    private String getOpenId(String code) {
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }
}




