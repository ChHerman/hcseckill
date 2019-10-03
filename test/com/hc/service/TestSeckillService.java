package com.hc.service;

import com.hc.dto.Exposer;
import com.hc.dto.SeckilllExecution;
import com.hc.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class TestSeckillService {
    @Resource
    SeckillService seckillService;

    @Test
    public void testGetSeckillList(){
        List<Seckill> list = seckillService.getSeckillList();
        for (Seckill seckill:list) {
            System.out.println(seckill);
        }
    }

    @Test
    public void testGetSeckillById(){
        Seckill seckill = seckillService.getSeckillById(1000L);
        System.out.println(seckill);
    }

    @Test
    public void testExportSeckillUrl(){
        Exposer exposer = seckillService.exportSeckillUrl(1001L);
        System.out.println(exposer);
    }

    @Test
    public void testExcuteSeckill(){
        Long seckillId = 1002L;
        Long userPhone = 15555555565L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        String md5 = exposer.getMd5();
        System.out.println(md5);
        SeckilllExecution seckilllExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
        System.out.println(seckilllExecution);
    }
}
