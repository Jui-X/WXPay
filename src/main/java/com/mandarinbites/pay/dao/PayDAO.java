package com.mandarinbites.pay.dao;

import com.mandarinbites.pay.domain.PayInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PayDAO {

    @Select("SELECT id FROM pay_info WHERE open_id = #{open_id}")
    String queryByOpenId(@Param("open_id") String openId);

    @Select("SELECT id, trade_id, pay_status FROM pay_info WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "trade_id", property = "tradeId"),
            @Result(column = "pay_status", property = "status")
    })
    PayInfo queryPrePayInfo(@Param("id") int id);

    @Select("SELECT trade_id FROM pay_info WHERE prepay_id = #{prepay_id}")
    String queryByPrePayId(@Param("prepay_id") String prePayId);

    @Select("SELECT * FROM pay_info WHERE trade_id = #{trade_id}")
    PayInfo queryByTradeID(@Param("trade_id") String tradeId);

    @Insert({"INSERT INTO pay_info(trade_id, open_id, pay_status) VALUES(#{trade_id}, #{open_id}, #{pay_status})"})
    int prePayUnifiedOrder(@Param("trade_id") String tradeId, @Param("open_id") String openId, @Param("pay_status") int status);

    @Update("UPDATE pay_info SET prepay_id = #{prepay_id} WHERE open_id = #{open_id}")
    void updatePrePayID(@Param("prepay_id") String prePayId, @Param("open_id") String openId);

    @Update("UPDATE pay_info SET phone_number = #{phone_number}, email = #{email}, referees = #{referees}, pay_status = #{pay_status} " +
            "WHERE trade_id = #{trade_id}")
    void updatePayInfo(@Param("phone_number") String phoneNumber, @Param("email") String email, @Param("referees") String referees,
                       @Param("pay_status") int payStatus, @Param("trade_id") String tradeId);

    @Update("UPDATE pay_info SET pay_status = #{pay_status} WHERE trade_id = #{trade_id}")
    void updatePayStatus(@Param("pay_status") int payStatus, @Param("trade_id") String tradeId);
}
