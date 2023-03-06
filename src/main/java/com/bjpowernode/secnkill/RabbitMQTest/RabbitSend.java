package com.bjpowernode.secnkill.RabbitMQTest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RabbitSend {
    @Resource
    private RabbitTemplate rabbitTemplate;
    /*
    测试
    public void  send(Object msg){
        log.info("发送信息:"+msg);
        rabbitTemplate.convertAndSend("queue",msg);
    }

    //faout
    public  void send1(Object msg){
        log.info("发送消息:"+msg);
        rabbitTemplate.convertAndSend("exchange","",msg);
    }

    //direct
    public void send2(Object msg){
        log.info("发送消息"+msg);
        rabbitTemplate.convertAndSend("exchange2","red",msg);
    }
    public void send3(Object msg){
        log.info("发送消息"+msg);
        rabbitTemplate.convertAndSend("exchange2","green",msg);
    }

    //topic
    public void send4(Object msg){
        log.info("发送信息(两个都接收到):"+msg);
        rabbitTemplate.convertAndSend("exchange_topic","ddd.queue.aaa",msg);
    }
    public void send5(Object msg){
        log.info("发送信息(单个接收到):"+msg);
        rabbitTemplate.convertAndSend("exchange_topic","ddd.ccc",msg);
    }

    //Header
    public void send6(String msg){
        log.info("发送信息(两个都能接收到)"+msg);
        MessageProperties properties=new MessageProperties();
        properties.setHeader("name","lishi");
        properties.setHeader("sum","121212");
        Message message=new Message(msg.getBytes(),properties);
         rabbitTemplate.convertAndSend("exchange_header","",message);
    }
    public void send7(String msg){
        log.info("发送信息(单个能接收到)"+msg);
        MessageProperties properties=new MessageProperties();
        properties.setHeader("name","lishi");
        Message message=new Message(msg.getBytes(),properties);
        rabbitTemplate.convertAndSend("exchange_header","",message);
    }*/
    public void seckillSend(Object msg){
        log.info("发送秒杀信息"+msg);
        rabbitTemplate.convertAndSend("seckill_exchange","seckill.message",msg);
    }
}
