package com.mandarinbites.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.mandarinbites.pay.domain.PayInfo;
import com.mandarinbites.pay.exception.PayException;
import com.mandarinbites.pay.service.PayService;
import com.mandarinbites.pay.utils.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @param: none
 * @description:
 * @author: JuiX
 * @create: 2019-12-14 21:24
 **/
@RequestMapping("/weixin/pay")
@RestController
@Log4j2
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/payByJSAPI")
    public JsonResult payByJSAPI(@RequestBody(required = true) JSONObject payParam, HttpServletRequest request) {
        try {
            String phoneNumber = payParam.getString("phoneNumber");
            String email = payParam.getString("email");
            String referees = payParam.getString("referees");
            String clientIP = request.getRemoteAddr();
            String openId = payParam.getString("openId");
            BigDecimal fee = payParam.getBigDecimal("fee");

            PayInfo payInfo = payService.prePayUnifiedOrder(phoneNumber, email, referees);

            Map<String, String> prePayResult = payService.wxPayByJSAPI(payInfo.getTradeId(), clientIP, openId, fee);

            return JsonResult.ok(prePayResult);
        } catch (PayException e) {

            return JsonResult.errorException(e.getMessage());
        } catch (Exception ex) {

            return JsonResult.errorMsg(ex.getMessage());
        }
    }
}
