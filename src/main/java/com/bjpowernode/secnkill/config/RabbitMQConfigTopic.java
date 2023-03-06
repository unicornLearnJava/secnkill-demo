package com.bjpowernode.secnkill.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// *代表一个单词  #代表一个或多个单词
@Configuration
public class RabbitMQConfigTopic {
    /*
    测试
    public static final  String QUEUE_01="queue_topic01";
    public static final  String QUEUE_02="queue_topic02";
    public static final  String EXCHANGE="exchange_topic";
    public static final  String ROUDINGKEY01="*.queue.*";
    public static final  String ROUDINGKEY02="ddd.#";
    @Bean
    public Queue queue001(){
        return new Queue(QUEUE_01);
    }
    @Bean
    public Queue queue002(){
        return new Queue(QUEUE_02);
    }
    @Bean
    public TopicExchange exchange3(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding binding001(){
        return BindingBuilder.bind(queue001()).to(exchange3()).with(ROUDINGKEY01);
    }
    @Bean
    public Binding binding002(){
        return BindingBuilder.bind(queue002()).to(exchange3()).with(ROUDINGKEY02);
    }*/
    public static final String QUEUE_NAME01="seckill_queue";
    public static final String EXCHANGE="seckill_exchange";
    @Bean
    public Queue seckillQueue(){
        return new Queue(QUEUE_NAME01);
    }
    @Bean
    public TopicExchange seckillExchange(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding seckillBinDing(){
        return BindingBuilder.bind(seckillQueue()).to(seckillExchange()).with("seckill.#");
    }
}
