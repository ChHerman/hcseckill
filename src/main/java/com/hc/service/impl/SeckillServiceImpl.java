package com.hc.service.impl;

import com.hc.dao.SeckillDao;
import com.hc.dao.SuccessKilledDao;
import com.hc.dao.cache.RedisDao;
import com.hc.dto.Exposer;
import com.hc.dto.SeckilllExecution;
import com.hc.entity.Seckill;
import com.hc.entity.SuccessKilled;
import com.hc.enums.SeckillStatEnum;
import com.hc.exception.RepeatKillException;
import com.hc.exception.SeckillCloseException;
import com.hc.exception.SeckillException;
import com.hc.service.SeckillService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    @Resource
    private SeckillDao seckillDao;
    @Resource
    private SuccessKilledDao successKilledDao;
    @Resource
    private RedisDao redisDao;
    private final String salt = "aksehiucka24sf*&%&^^#^%$";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll();
    }

    @Override
    public Seckill getSeckillById(Long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null){
                return new Exposer(false, seckillId);
            }
        }
        Date nowTime = new Date();
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        if (nowTime.getTime()<startTime.getTime() || nowTime.getTime()>endTime.getTime()){
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(Long seckillId){
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public SeckilllExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillException {
        if (md5 == null||!md5.equals(getMd5(seckillId))){
            throw new SeckillException("seckill data rewrite!");
        }
        try {
            Date nowTime = new Date();
            int insertResult = successKilledDao.insertSuccessKilled(seckillId, userPhone, nowTime);
            if (insertResult<=0){
                throw new RepeatKillException("seckill repeat");
            } else {
                int reduceResult = seckillDao.reduceNumber(seckillId,nowTime);
                if (reduceResult<=0){
                    throw new SeckillCloseException("seckill close");
                }else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckilllExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        }catch (RepeatKillException e){
            throw e;
        }catch (SeckillCloseException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
