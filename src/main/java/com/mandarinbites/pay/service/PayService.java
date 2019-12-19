package com.mandarinbites.pay.service;

import com.mandarinbites.pay.domain.AccessToken;
import com.mandarinbites.pay.domain.PayInfo;

import java.math.BigDecimal;
import java.util.Map;

public interface PayService {

    AccessToken getAccessTokenByCode(String code);

    String validatePayResult(String xml) throws Exception;

    PayInfo prePayUnifiedOrder(String openId) throws Exception;

    Map<String, String> wxPayByJSAPI(String tradeId, String clientIP, String openId, BigDecimal fee) throws Exception;

    Map<String, String> checkPayStatus(String phoneNumber, String email, String referees, String prePayId) throws Exception;
}
