package com.innovation.warm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.innovation.warm.constant.RedisConstant;
import com.innovation.warm.domain.dto.UserLoginDTO;
import com.innovation.warm.enumeration.ResultCodeEnum;
import com.innovation.warm.exception.ServiceException;
import com.innovation.warm.pojo.entity.UserLogin;
import com.innovation.warm.properties.JwtProperties;
import com.innovation.warm.properties.WeChatProperties;
import com.innovation.warm.service.UserLoginService;
import com.innovation.warm.mapper.UserLoginMapper;
import com.innovation.warm.util.HttpClientUtil;
import com.innovation.warm.util.JwtUtil;
import com.innovation.warm.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserLoginMapper userLoginMapper;

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private RedisUtil redisUtil;

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
        // 该用户注册过了 / 登录过了返回token  且把用户信息存入到redis 中 key是 前缀 + 用户的 唯一标识id  可以用来实现剔除下线功能
        // token里面 加密的内容是用户的id
        return jwtUtil.createToken(one);
    }

    @Override
    public void updateUserInfo(UserLoginDTO userLoginDTO) {
        userLoginMapper.updateUserInfo(userLoginDTO);
        UserLogin userLogin = this.getById(userLoginDTO.getId());
        // 修改redis的登录用户数据
        redisUtil.set(RedisConstant.USER_LOGIN_CACHE + userLogin.getId(), userLogin, RedisConstant.EXPIRE_TIME, TimeUnit.MINUTES);
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




