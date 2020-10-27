package com.atguigu.springcloud.dao;


import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {

    //一般业内的添加操作都是  add  save  create
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);

}
