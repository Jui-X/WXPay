package com.mandarinbites.pay.service.impl;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.mandarinbites.pay.config.WXPayProperties;
import com.mandarinbites.pay.dao.PayDAO;
import com.mandarinbites.pay.domain.AccessToken;
import com.mandarinbites.pay.domain.PayInfo;
import com.mandarinbites.pay.enums.PayStatusEnums;
import com.mandarinbites.pay.exception.PayException;
import com.mandarinbites.pay.service.PayService;
import com.mandarinbites.pay.utils.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.mandarinbites.pay.enums.PayExceptionEnums.GET_ACCESS_TOKEN_FAIL;
import static com.mandarinbites.pay.enums.PayExceptionEnums.PAY_RESULT_FAIL;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-14 21:50
 **/
@Service
public class PayServiceImpl implements PayService {

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    private PayDAO payDAO;

    @Autowired
    private WXPayClient wxPayClient;

    @Autowired
    private WXPayProperties properties;

    @Override
    public AccessToken getAccessTokenByCode(String code) {
        String appId = properties.getAppId();
        String appSecret = properties.getAppSecret();

        AccessToken accessToken = AccessTokenUtil.getAccessToken(code, appId, appSecret);

        String openId = accessToken.getOpenid();
        if (openId == null || "".equals(openId)) {
            throw new PayException(GET_ACCESS_TOKEN_FAIL.getCode(), GET_ACCESS_TOKEN_FAIL.getMsg());
        }

        return accessToken;
    }

    @Override
    public String validatePayResult(String xml) throws Exception {
        if (WXPayUtil.isSignatureValid(xml, properties.getApiKey())) {
            return "";
        }

        Map<String, String> map = WXPayUtil.xmlToMap(xml);
        String out_trade_no = map.get("out_trade_no");
        if (out_trade_no == null || "".equals(out_trade_no)) {
            throw new PayException(PAY_RESULT_FAIL.getCode(), PAY_RESULT_FAIL.getMsg());
        }
        PayInfo payInfo = payDAO.queryByTradeID(out_trade_no);
        if (payInfo != null) {
            payDAO.updatePayStatus(PayStatusEnums.SUCCESS.getStatus(), out_trade_no);
        }
        map = new HashMap<>();
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "OK");

        return WXPayUtil.mapToXml(map);
    }

    @Override
    public PayInfo prePayUnifiedOrder(String openId) {
        UUID uuid = UUID.randomUUID();
        String tradeId = uuid.toString().replace("-", "");
        int payStatus = PayStatusEnums.NOT_PAID.getStatus();

        payDAO.prePayUnifiedOrder(tradeId, openId, payStatus);

        PayInfo payInfo = new PayInfo();
        payInfo.setTradeId(tradeId);
        payInfo.setOpenId(openId);
        payInfo.setStatus(payStatus);

        // if (status != PayStatusEnums.NOT_PAID.getStatus()) {
        //     throw new PayException(PRE_PAY_STATUS_ERROR.getCode(), PRE_PAY_STATUS_ERROR.getMsg());
        // }

        return payInfo;
    }

    @Override
    public Map<String, String> wxPayByJSAPI(String tradeId, String clientIP, String openId, String fee) throws Exception {
        String appId = properties.getAppId();
        String mchId = properties.getMchId();
        String apiKey = properties.getApiKey();

        Map<String, String> responseMap = wxPayClient.payByJSAPI(WXPayConstants.SignType.MD5, false, appId, mchId,
                apiKey, tradeId, clientIP, openId, fee);

        payDAO.updatePrePayID(responseMap.get("prepay_id"), openId, tradeId);

        return responseMap;
    }

    @Override
    public Map<String, String> checkPayStatus(String phoneNumber, String email, String referees, String prePayId) throws Exception {
        String tradeId = payDAO.queryByPrePayId(prePayId);
        String appId = properties.getAppId();
        String mchId = properties.getMchId();
        String apiKey = properties.getApiKey();

        Map<String, String> queryResponse = wxPayClient.orderQuery(WXPayConstants.SignType.MD5, true, appId, mchId, apiKey, tradeId);

        if (SUCCESS.equals(queryResponse.get("return_code")) && SUCCESS.equals(queryResponse.get("result_code")) && SUCCESS.equals(queryResponse.get("trade_state"))) {
            payDAO.updatePayInfo(phoneNumber, email, referees, PayStatusEnums.SUCCESS.getStatus(), tradeId);
        } else {
            throw new PayException(Integer.parseInt(queryResponse.get("err_code")), queryResponse.get("err_code_des"));
        }

        return queryResponse;
    }
}
