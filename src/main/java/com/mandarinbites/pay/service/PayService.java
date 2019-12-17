package com.mandarinbites.pay.service;

import com.mandarinbites.pay.domain.PayInfo;
import com.mandarinbites.pay.exception.PayException;

import java.math.BigDecimal;
import java.util.Map;

public interface PayService {

    PayInfo prePayUnifiedOrder(String phoneNumber, String email, String referees) throws PayException, Exception;

    Map<String, String> wxPayByJSAPI(String traceId, String clientIP, String openId, BigDecimal fee) throws PayException, Exception;
}
