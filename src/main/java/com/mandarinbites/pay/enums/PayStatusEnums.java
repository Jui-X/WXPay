package com.mandarinbites.pay.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnums {
    FAIL(0, "支付失败"),
    SUCCESS(1, "支付成功"),
    NOT_PAID(2, "尚未支付")
    ;

    private int status;
    private String desp;

    PayStatusEnums(int status, String desp) {
        this.status = status;
        this.desp = desp;
    }
}
