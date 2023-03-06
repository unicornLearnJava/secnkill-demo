package com.bjpowernode.secnkill.vo;

//使用rabbitmq秒杀所要发送的信息实体类

import com.bjpowernode.secnkill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {
    private User user;
    private Integer goodsId;
}
