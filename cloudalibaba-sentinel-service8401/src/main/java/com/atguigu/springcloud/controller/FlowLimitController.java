package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {


    @GetMapping("/testA")
    public String testA(){
        return "--------aaa";
    }

    @GetMapping("/testB")
    public String testB(){
        return "--------bbb";
    }


    @GetMapping("/testD")
    public String testD(){
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
           e.printStackTrace();
        }
        log.info("test 测试RT");
        return "--------bbb";
    }

    @GetMapping("/testHotkey")
    @SentinelResource(value = "testHotkey",blockHandler = "del_testHotkey")
    public String testHotkey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p1",required = false)String p2){
       return "------hotkey";
    }


    //错误兜底的方法
    public String del_testHotkey(String p1, String p2, BlockException e){
        return "------del_testHotkey,/(ㄒoㄒ)/~~";        //sentinel 的默认提示：Block by sentinel(flow limiting)
    }
}
