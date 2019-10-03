package com.hc.service;

import com.hc.dto.Exposer;
import com.hc.dto.SeckilllExecution;
import com.hc.entity.Seckill;
import com.hc.exception.RepeatKillException;
import com.hc.exception.SeckillCloseException;
import com.hc.exception.SeckillException;

import java.util.Date;
import java.util.List;

public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getSeckillById(Long seckillId);

    Exposer exportSeckillUrl(Long seckillId);

    SeckilllExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws
            RepeatKillException,
            SeckillCloseException,
            SeckillException;
}
