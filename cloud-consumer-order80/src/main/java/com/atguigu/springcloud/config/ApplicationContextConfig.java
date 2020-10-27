package com.atguigu.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    /*想使用restTemplate 需要先将他引入到容器中*/
//    @Autowired
//    private RestTemplate restTemplate;

    /*也可以重载这个模块，自定义他再引用   */
    /*@LoadBalanced注解 赋予了RestTemplate负载均衡的能力 默认是轮询算法机制 */
    @Bean
    //@LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


}
