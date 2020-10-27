package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.demoan.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    //新建订单
    void createOrder(Order order);

    //修改订单状态 代表订单已完成
    void update(@Param("userId")Long userId,@Param("status")Integer status);


}
