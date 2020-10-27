package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.OrderDao;
import com.atguigu.springcloud.demoan.Order;
import com.atguigu.springcloud.service.AccountService;
import com.atguigu.springcloud.service.OrderService;
import com.atguigu.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     *   创建订单-》调用库存服务扣减库存-》调用账户服务扣减账户余额-》修改订单状态 完成订单的创建
     *   下订单-> 减库存 -> 减余额 -> 改状态
     * @param order
     */


    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("-----> 订单创建");
        orderDao.createOrder(order);
        log.info("-----> 订单微服务开始调用库存，做减扣Count");
        //调用storageService中的方法 扣减库存
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("-----> 订单微服务开始调用库存，做减扣结束END");
          //扣钱 扣减账户
        log.info("-----> 订单微服务开始调用账户，做减扣Money");
        accountService.decrease(order.getUserId(),order.getMoney());

        //修改订单的状态
        log.info("-----> 修改订单状态");
        orderDao.update(order.getUserId(),0);
        log.info("-----> 修改订单状态结束");

        log.info("-----> 下订单结束，o(*￣▽￣*)ブ");
    }
}
