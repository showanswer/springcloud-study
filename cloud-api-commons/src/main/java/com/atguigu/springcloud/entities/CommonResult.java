package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    //404 not find
    private Integer code;
    //返回错误消息，具体的错误信息
    private String message;
    //返回的值 后端给前端的值
    private T data;

    public CommonResult(Integer code,String message){
         this(code,message,null);
    }

}
