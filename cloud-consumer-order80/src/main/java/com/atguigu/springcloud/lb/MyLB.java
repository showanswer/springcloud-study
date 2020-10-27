package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer {

    //设置一个原子整型参数  作为请求的次数 第几次请求 0 为没有开始请求
    private AtomicInteger atomicInteger=new AtomicInteger(0);

    //该方法为了获取用户的访问次数   创建自询锁,线程问题
    public final int getAndIncrement(){
         int current;
         int next;

         do {
             // 获取常量值为0 即还没有开始请求
             current=this.atomicInteger.get();
             // 获取请求次数  第一次调度即 为1
             next=current >= 2147483647 ? 0:current+1;
             //atomicInteger比较并且设置  current是期望值 atomicInteger的原值和期望值做比较如果为true  就是两者值相同 就更改atomicInteger为next
         }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("******第几次访问次数，次数next:"+next);
        return next;


    }


    /**
     *
     * @param serviceInstances  是总共服务器的数量  不是可用服务器的数量
     * @return   serviceInstances.get(index)  返回的是第几个服务器
     */
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {

       int index= getAndIncrement() % serviceInstances.size();

        return serviceInstances.get(index);
    }
}
