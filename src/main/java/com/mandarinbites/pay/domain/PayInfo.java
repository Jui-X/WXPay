package com.mandarinbites.pay.domain;

import lombok.Data;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-15 22:57
 **/
@Data
public class PayInfo {

    /** 商户订单ID */
    private String tradeId;

    /** 手机号 */
    private String phoneNumber;

    /** 邮箱地址 */
    private String email;

    /** 推荐人 */
    private String referees;

    /** 订单状态 */
    private int status;
}
