package com.mandarinbites.pay.utils;

import com.alibaba.fastjson.JSONObject;
import com.mandarinbites.pay.config.WXPayProperties;
import com.mandarinbites.pay.domain.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-19 00:14
 **/
public class AccessTokenUtil {
    private static String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={?}&secret={?}&code={?}&grant_type=authorization_code";

    private static RestTemplate restTemplate;

    @Autowired
    private WXPayProperties properties;

    static {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public static AccessToken getAccessToken(String code, String appId, String appSecret) {

        String response = restTemplate.getForObject(url, String.class, appId, appSecret, code);

        AccessToken accessToken = JSONObject.parseObject(response, AccessToken.class);

        return accessToken;
    }
}
