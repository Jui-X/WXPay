package com.mandarinbites.pay.exception;

import com.mandarinbites.pay.enums.PayExceptionEnums;
import lombok.Getter;

import static com.mandarinbites.pay.enums.PayExceptionEnums.ANOTHER_EXCEPTION;

/**
 * @param: none
 * @description:
 * @author: JuiX
 * @create: 2019-12-14 22:24
 **/
@Getter
public class PayException extends RuntimeException {

    private int exceptionCode;
    private String extraInfo;

    public PayException(int exceptionCode, String msg) {
        super(msg);
        this.exceptionCode = exceptionCode;
    }

    public PayException(PayExceptionEnums exceptionCode) {
        this(exceptionCode.getCode(), exceptionCode.getMsg());
    }

    public PayException(String exceptionMsg) {
        this(ANOTHER_EXCEPTION, exceptionMsg);
    }

    public PayException(PayExceptionEnums exceptionCode, String exceptionMsg) {
        this(exceptionCode.getCode(), exceptionMsg);
    }
}
