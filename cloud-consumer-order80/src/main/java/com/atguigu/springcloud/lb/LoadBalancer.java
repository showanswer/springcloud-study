package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    /**
     * 这个方法用于收集现在注册中心有多少台服务器
     * @param serviceInstances
     * @return
     */
     ServiceInstance instance(List<ServiceInstance> serviceInstances);

}
