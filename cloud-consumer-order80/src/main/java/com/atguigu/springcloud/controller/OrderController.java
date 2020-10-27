package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //public static final String PAYMENT_URL="http://localhost:8001";
    //直接调用微服务的名称
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

   @GetMapping("/consumer/payment/create")
   public CommonResult<Payment> create(Payment payment){
       //（url,requestMap,ResponseBean.class）这三个参数分别代表：REST请求地址、请求参数、HTTP响应转换被转换成的对象类型
     return   restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
       //  postForEntity  post方式传输参数调用
      // return restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment,CommonResult.class).getBody();
   }


   @GetMapping("/consumer/payment/get/{id}")
   public CommonResult<Payment> getPayment(@PathVariable("id")Long id)
   {
          //客户端向服务器 发送请求
          return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
   }

   /*getForEntity  获取到的返回值中  带有请求体  状态码 响应头信息 等等*/
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id")Long id){
        ResponseEntity<CommonResult> entity=restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);

        if (entity.getStatusCode().is2xxSuccessful()){
            //返回请求体
            return entity.getBody();
        }else {
            return new CommonResult<>(444,"操作失败");
        }
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){

        //根据指定服务器名称 获取他们的集群数量名称等相关信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        //如果没有这个服务器或者改服务器里的集群数量为0
        if(instances == null || instances.size() <=0){
             return null;
        }
        ServiceInstance instance = loadBalancer.instance(instances);
        //获取改服务器的信息 IP地址
        URI uri = instance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }


    @GetMapping("/consumer/payment/zipkin")
    public String getPaymentZipkin()
    {
        //客户端向服务器 发送请求
        return  restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin",String.class);
    }



}
