package com.hc.web;

import com.hc.dto.Exposer;
import com.hc.dto.SeckillResult;
import com.hc.dto.SeckilllExecution;
import com.hc.entity.Seckill;
import com.hc.enums.SeckillStatEnum;
import com.hc.exception.RepeatKillException;
import com.hc.exception.SeckillCloseException;
import com.hc.service.SeckillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Resource
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        Seckill seckill = seckillService.getSeckillById(seckillId);
        model.addAttribute(seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
                    method = RequestMethod.GET,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e){
            e.printStackTrace();
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckilllExecution> execution(@PathVariable("seckillId") Long seckillId,
                                                      @PathVariable("md5") String md5,
                                                      @CookieValue(value = "userPhone", required = false) Long userPhone){
        if (userPhone == null){
            return new SeckillResult<>(false, "未注册");
        }
        try {
            SeckilllExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<SeckilllExecution>(true, execution);
        } catch (RepeatKillException e) {
            SeckilllExecution execution = new SeckilllExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckilllExecution>(true, execution);
        } catch (SeckillCloseException e) {
            SeckilllExecution execution = new SeckilllExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckilllExecution>(true, execution);
        } catch (Exception e) {
            SeckilllExecution execution = new SeckilllExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckilllExecution>(true, execution);
        }
    }

    @RequestMapping(value = "/time/now",
                    method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }
}
