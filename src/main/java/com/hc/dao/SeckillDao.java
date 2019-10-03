package com.hc.dao;

import com.hc.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    int reduceNumber(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);

    Seckill queryById(@Param("seckillId") Long seckillId);

    List<Seckill> queryAll();
}
