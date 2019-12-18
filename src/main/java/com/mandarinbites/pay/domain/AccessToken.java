package com.mandarinbites.pay.domain;

import lombok.Data;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-19 00:18
 **/
@Data
public class AccessToken {
    private String access_token;

    private String refresh_token;

    private String openid;

    private String scope;
}
