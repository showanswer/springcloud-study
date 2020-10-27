package com.atguigu.springcloud.demoan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    //订单的状态 0：创建中  1：已完结
    private Integer status;
    private BigDecimal money;



}
