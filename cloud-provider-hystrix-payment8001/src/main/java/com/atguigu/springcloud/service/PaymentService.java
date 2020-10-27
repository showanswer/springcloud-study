package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    //服务降级
    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+"    paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O";
    }


    /**
     * 超时链接 故障
     *
     * @HystrixCommand 的  fallbackMethod是服务降级后兜底的方法，出现错误后运行指定的方法   系统出错就会调用指定的方法
     * @HystrixCommand 的  commandProperties中可以设置@HystrixProperty注解，里面封装什么情况下的故障 当前的故障为延时3秒内正常超过三秒锁故障
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value ="3000" )
    })
    public String paymentInfo_TimeOut(Integer id){
        int timeNumber=3;
        try {
            //Thread.sleep(3000);  线程暂停三秒  。TimeUnit.SECONDS.sleep(3)程序在这里暂停三秒
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+"    paymentInfo_TimeOut,id: "+id+"\t"+"O(∩_∩)O   耗时(s)"+timeNumber;
    }

    public String paymentInfo_TimeOutHandler(Integer id){

        return "线程池："+Thread.currentThread().getName()+" 系统繁忙或者运行出错，请稍后再试,id: "+id+"\t"+"/(ㄒoㄒ)/~~";
    }

    //服务熔断
   @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
           @HystrixProperty(name ="circuitBreaker.enabled" ,value ="true" ),//是否开启断路器
           @HystrixProperty(name ="circuitBreaker.requestVolumeThreshold" ,value ="10" ),//设置请求次数
           @HystrixProperty(name ="circuitBreaker.sleepWindowInMilliseconds" ,value ="10000" ),//设置时间范围  时间窗口期
           @HystrixProperty(name ="circuitBreaker.errorThresholdPercentage" ,value ="60" )//设置失败率达到多少后跳闸
   })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id <0){
            throw new RuntimeException("id不能为负数");
        }
        //IdUtil.simpleUUID()  ==  UUID.randomUUID();
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功,流水号："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试,/(ㄒoㄒ)/~~  id:"+id;
    }


}
