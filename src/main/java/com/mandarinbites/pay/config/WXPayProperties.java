package com.mandarinbites.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-15 22:17
 **/
@Data
@Component
@ConfigurationProperties(prefix = "wxpay")
public class WXPayProperties {

    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     *
     */
    private String appSecret;

    /**
     *
     */
    private String apiKey;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    /**
     * 微信异步通知回调URL
     */
    private String notifyUrl;

    /**
     *
     */
    private String refundUrl;

    /**
     * 交易方式，此处默认JSAPI
     */
    private String tradeType;
}
