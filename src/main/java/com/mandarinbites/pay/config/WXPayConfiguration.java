package com.mandarinbites.pay.config;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-15 22:17
 **/
public class WXPayConfiguration implements WXPayConfig {

    private String appId;
    private String mchId;
    private String mchKey;

    public WXPayConfiguration(String appId, String mchId, String mchKey) {
        this.appId = appId;
        this.mchId = mchId;
        this.mchKey = mchKey;
    }

    @Override
    public String getAppID() {
        return this.appId;
    }

    @Override
    public String getMchID() {
        return this.mchId;
    }

    @Override
    public String getKey() {
        return this.mchKey;
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
