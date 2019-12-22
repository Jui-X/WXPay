package com.mandarinbites.pay.service.impl;


import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.mandarinbites.pay.config.WXPayConfiguration;
import com.mandarinbites.pay.config.WXPayProperties;
import com.mandarinbites.pay.exception.PayException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.mandarinbites.pay.enums.PayExceptionEnums.PAY_FAIL;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-14 23:51
 **/
@Log4j2
@Component
public class WXPayClient {

    @Autowired
    private WXPayProperties properties;

    public Map<String, String> payByJSAPI(WXPayConstants.SignType signType, boolean sandBox, String tradeId, String clientIP, String openId, String fee) throws Exception, PayException {
        WXPayConfiguration configuration = new WXPayConfiguration();
        WXPay wxPay = new WXPay(configuration, signType, sandBox);

        Map<String, String> data = new HashMap<>();
        data.put("body", "一口汉语在线课程");
        data.put("out_trade_no", tradeId);
        data.put("total_fee", String.valueOf(fee));
        data.put("spbill_create_ip", clientIP);
        data.put("notify_url", properties.getNotifyUrl());
        data.put("trade_type", properties.getTradeType());
        data.put("openid", openId);

        data = wxPay.fillRequestData(data);

        Map<String, String> response = wxPay.unifiedOrder(data);

        String prePayId = response.get("prepay_id");
        if (prePayId == null || "".equals(prePayId)) {
            throw new PayException(PAY_FAIL.getCode(), PAY_FAIL.getMsg());
        }

        return response;
    }

    public Map<String, String> orderQuery(WXPayConstants.SignType signType, boolean sandBox, String tradeId) throws Exception {
        WXPayConfiguration configuration = new WXPayConfiguration();
        WXPay wxPay = new WXPay(configuration, signType, sandBox);

        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", tradeId);

        Map<String, String> queryResponse = wxPay.orderQuery(data);

        return queryResponse;
    }
}
