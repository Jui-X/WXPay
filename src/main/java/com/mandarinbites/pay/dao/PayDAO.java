package com.mandarinbites.pay.dao;

import com.mandarinbites.pay.domain.PayInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PayDAO {

    @Select("SELECT id, trade_id, pay_status FROM pay_info WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "trade_id", property = "tradeId"),
            @Result(column = "pay_status", property = "status")
    })
    PayInfo queryPrePayInfo(@Param("id") int id);

    @Insert("INSERT INTO pay_info(trade_id, phone_number, email, referees, pay_status) VALUES(trade_id, phone_number, email, referees, pay_status)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int prePayUnifiedOrder(@Param("trade_id") String tradeId, @Param("phone_number") String phoneNumber, @Param("email") String email, @Param("referees") String referees,
                          @Param("pay_status") int status);

    @Update("UPDATE pay_info SET prepay_id = #{prepay_id}")
    void updatePrePayID(@Param("prepay_id") String prePayId);
}
