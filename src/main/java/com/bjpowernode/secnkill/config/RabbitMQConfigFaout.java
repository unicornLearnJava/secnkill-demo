package com.bjpowernode.secnkill.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfigFaout {
    public static  final  String QUEUE_01="queue_01";
    public static  final  String  QUEUE_02="queue_02";
    public static  final  String  EXCHANGE="exchange";
    @Bean
    public Queue queue(){
        return new Queue("queue",true);
    }
    @Bean
    public Queue queue1(){
        return new Queue(QUEUE_01,false);
    }
    @Bean
    public Queue queue2(){
        return new Queue(QUEUE_02,false);
    }
    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange(EXCHANGE);
    }
    @Bean
    public Binding cindign1(){
        return BindingBuilder.bind(queue1()).to(exchange());
    }
    @Bean
    public Binding cindign2(){
        return BindingBuilder.bind(queue2()).to(exchange());
    }
}
