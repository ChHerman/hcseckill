package com.hc.dao;

import com.hc.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface SuccessKilledDao {
    /**
     *
     * @param seckillId
     * @param phone
     * @param createTime
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") Long seckillId, @Param("phone") Long phone, @Param("createTime") Date createTime);

    /**
     *
     * @param seckillId
     * @param phone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") Long seckillId, @Param("phone") Long phone);
}
