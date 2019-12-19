package com.mandarinbites.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.mandarinbites.pay.domain.AccessToken;
import com.mandarinbites.pay.domain.PayInfo;
import com.mandarinbites.pay.exception.PayException;
import com.mandarinbites.pay.service.PayService;
import com.mandarinbites.pay.utils.JsonResult;
import com.mandarinbites.pay.utils.QRCodeGenerator;
import com.mandarinbites.pay.utils.Stream2StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private static final String URL = "pay.hskzhhy.cn/weixin/pay/index.html";

    @Autowired
    private PayService payService;

    @GetMapping
    public JsonResult getAccessTokenByCode(String code) {
        try {
            AccessToken accessToken = payService.getAccessTokenByCode(code);

            return JsonResult.ok(accessToken);
        } catch (Exception ex) {
            return JsonResult.errorException(ex.getMessage());
        }
    }

    @PostMapping("/payByJSAPI")
    public JsonResult payByJSAPI(@RequestBody(required = true) JSONObject prePayParam, HttpServletRequest request) {
        try {
            String clientIP = request.getRemoteAddr();
            String openId = prePayParam.getString("openId");
            BigDecimal fee = prePayParam.getBigDecimal("fee");

            PayInfo payInfo = payService.prePayUnifiedOrder(openId);

            Map<String, String> prePayResult = payService.wxPayByJSAPI(payInfo.getTradeId(), clientIP, openId, fee);

            return JsonResult.ok(prePayResult);
        } catch (PayException e) {

            return JsonResult.errorException(e.getMessage());
        } catch (Exception ex) {

            return JsonResult.errorMsg(ex.getMessage());
        }
    }

    @PostMapping("/notify")
    public JsonResult WXNotify(HttpServletRequest request) {
        try {
            String xml = Stream2StringUtil.parseStream2XML(request);

            String payResult = payService.validatePayResult(xml);

            return JsonResult.ok(payResult);
        } catch (PayException e) {
            return JsonResult.errorMsg(e.getMessage());
        } catch (Exception ex) {
            return JsonResult.errorException(ex.getMessage());
        }
    }

    @PostMapping("/checkPayStatus")
    public JsonResult checkPayStatus(@RequestBody(required = true) JSONObject payParam) {
        try {
            String phoneNumber = payParam.getString("phoneNumber");
            String email = payParam.getString("email");
            String referees = payParam.getString("referees");
            String prePayId = payParam.getString("prePayId");

            // TODO 重复支付

            Map<String, String> queryResult = payService.checkPayStatus(phoneNumber, email, referees, prePayId);

            return JsonResult.ok(queryResult);
        } catch (Exception e) {

            return JsonResult.errorMsg(e.getMessage());
        }
    }

    @RequestMapping("/getQRCode")
    public void getQRCode(HttpServletResponse response) {
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();

        qrCodeGenerator.QRCodeGenerate(response, URL);
    }
}
