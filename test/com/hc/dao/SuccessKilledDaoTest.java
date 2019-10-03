package com.hc.dao;

import com.hc.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled(){
        Long seckillId = 1000L;
        Long userPhone = 17777777777L;
        Byte state = 0;
        Date createTime = new Date();
        int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone, createTime);
        System.out.println(insertCount);
    }
    @Test
    public void testQueryByIdWithSeckill(){
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1000L,17777777777L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill().toString());
    }

}
