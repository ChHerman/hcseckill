package com.hc.dao;

import com.hc.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SeckillDaoTest {
    @Resource
    SeckillDao seckillDao;

    @Test
    public void testQueryById(){
        Seckill seckill = seckillDao.queryById(1000L);
        System.out.println(seckill);
    }
    @Test
    public void testReduceNumber(){
        int i = seckillDao.reduceNumber(1001L, new Date()
        );
    }
    @Test
    public void testQueryAll(){
        List<Seckill> seckills = seckillDao.queryAll();
        for(Seckill seckill:seckills){
            System.out.println(seckill);
        }
    }
}
