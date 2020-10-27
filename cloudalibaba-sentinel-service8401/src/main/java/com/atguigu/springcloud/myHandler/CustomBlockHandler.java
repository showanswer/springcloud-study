package com.atguigu.springcloud.myHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;

public class CustomBlockHandler {

    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(444,"客户自定义的，Global handlerException------1");
    }
    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(444,"客户自定义的，Global handlerException-------2");
    }

}
