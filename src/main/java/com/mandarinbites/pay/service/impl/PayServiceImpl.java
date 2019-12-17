package com.mandarinbites.pay.service.impl;

import com.github.wxpay.sdk.WXPayConstants;
import com.mandarinbites.pay.dao.PayDAO;
import com.mandarinbites.pay.domain.PayInfo;
import com.mandarinbites.pay.enums.PayExceptionEnums;
import com.mandarinbites.pay.enums.PayStatusEnums;
import com.mandarinbites.pay.exception.PayException;
import com.mandarinbites.pay.service.PayService;
import com.mandarinbites.pay.service.WXPayClient;
import com.mandarinbites.pay.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.mandarinbites.pay.enums.PayExceptionEnums.PRE_PAY_STATUS_ERROR;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-14 21:50
 **/
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PayDAO payDAO;

    @Autowired
    private WXPayClient wxPayClient;

    @Override
    public PayInfo prePayUnifiedOrder(String phoneNumber, String email, String referees) throws PayException, Exception {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(uuid.toString().replace("-", ""));
        String tradeId = MD5Util.getMD5Str(sb.toString());
        int payStatus = PayStatusEnums.NOT_PAID.getStatus();
        int id = payDAO.prePayUnifiedOrder(tradeId, phoneNumber, email, referees, payStatus);
        if (id <= 0) {
            throw new PayException(PayExceptionEnums.PRE_PAY_FAIL.getCode(), PayExceptionEnums.PRE_PAY_FAIL.getMsg());
        }

        PayInfo prepayInfo = payDAO.queryPrePayInfo(id);
        int status = prepayInfo.getStatus();
        if (status != PayStatusEnums.NOT_PAID.getStatus()) {
            throw new PayException(PRE_PAY_STATUS_ERROR.getCode(), PRE_PAY_STATUS_ERROR.getMsg());
        }

        return prepayInfo;
    }

    @Override
    public Map<String, String> wxPayByJSAPI(String traceId, String clientIP, String openId, BigDecimal fee) throws PayException, Exception {

        return wxPayClient.payByJSAPI(WXPayConstants.SignType.MD5, true, traceId, clientIP, openId, fee);
    }
}
