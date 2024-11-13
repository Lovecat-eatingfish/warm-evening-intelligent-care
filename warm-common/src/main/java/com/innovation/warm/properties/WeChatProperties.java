package com.innovation.warm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: WeChatProperties
 * PackageName: com.innovation.warm.properties
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/14 上午12:18
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "warm.wechat")
public class WeChatProperties {
    //小程序的appid
    private String appid;
    //小程序的秘钥
    private String secret;
    //商户号
    private String mchid;
    //商户API证书的证书序列号
    private String mchSerialNo;
    //商户私钥文件
    private String privateKeyFilePath;
    //证书解密的密钥
    private String apiV3Key;
    //平台证书
    private String weChatPayCertFilePath;
    //支付成功的回调地址
    private String notifyUrl;
    //退款成功的回调地址
    private String refundNotifyUrl;
}
