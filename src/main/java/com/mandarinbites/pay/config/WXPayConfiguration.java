package com.mandarinbites.pay.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-15 22:17
 **/
@Configuration
public class WXPayConfiguration implements WXPayConfig {

    @Autowired
    private WXPayProperties properties;

    @Override
    public String getAppID() {
        return properties.getAppId();
    }

    @Override
    public String getMchID() {
        return properties.getMchId();
    }

    @Override
    public String getKey() {
        return properties.getMchKey();
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 5000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 8000;
    }
}
