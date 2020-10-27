package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class);
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class SeataMainApp2001 {

    public static void main(String[] args) {
        SpringApplication.run(SeataMainApp2001.class,args);
    }

}
