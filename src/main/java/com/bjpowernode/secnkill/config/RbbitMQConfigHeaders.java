package com.bjpowernode.secnkill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RbbitMQConfigHeaders {
    public static  final  String QUEUE_NAME01="queue_header01";
    public static  final  String QUEUE_NAME02="queue_header02";
    public static  final  String EXCHANGE="exchange_header";

    @Bean
    public Queue queue_1(){
        return new Queue(QUEUE_NAME01);
    }
    @Bean
    public Queue queue_2(){
        return new Queue(QUEUE_NAME02);
    }
    @Bean
    public HeadersExchange exchange_1(){
        return new HeadersExchange(EXCHANGE);
    }
    @Bean
    public Binding binding_1(){
        Map<String,Object> map=new HashMap();
        map.put("name","lishi");
        map.put("age",12);
        return BindingBuilder.bind(queue_1()).to(exchange_1()).whereAny(map).match();
    }
    @Bean
    public Binding binding_2(){
        Map<String,Object> map =new HashMap();
        map.put("sum","121212");
        map.put("name","lishi");
        return BindingBuilder.bind(queue_2()).to(exchange_1()).whereAll(map).match();
    }



}
