package com.hc.dao.cache;

import com.hc.dao.SeckillDao;
import com.hc.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class RedisDaoTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Long seckillId = 1001L;
    @Resource
    private RedisDao redisDao;
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testRedisDao(){
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill==null){
            seckill = seckillDao.queryById(seckillId);
            if(seckill != null){
                String result = redisDao.putSeckill(seckill);
                logger.info("result={}", result);
                seckill = redisDao.getSeckill(seckillId);
                logger.info("seckill={}", seckill);
            }
        }
    }

}
