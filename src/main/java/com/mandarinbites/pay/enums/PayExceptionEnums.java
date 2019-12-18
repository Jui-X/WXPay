package com.mandarinbites.pay.enums;

import lombok.Getter;

@Getter
public enum PayExceptionEnums {

    PRE_PAY_SUCCESS(100, "预下单成功"),
    SUCCESS(200, "支付成功"),
    GET_ACCESS_TOKEN_FAIL(300, "获取用户openId失败"),
    PRE_PAY_FAIL(400, "预下单失败"),
    PRE_PAY_STATUS_ERROR(401, "预下单支付状态异常"),
    PAY_FAIL(500, "支付失败"),
    PAY_RESULT_FAIL(501, "支付结果校验失败"),

    ANOTHER_EXCEPTION(999, "其他异常")
    ;

    private int code;
    private String msg;

    PayExceptionEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
