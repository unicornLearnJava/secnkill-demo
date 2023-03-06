package com.bjpowernode.secnkill.config;



import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfigDirect {
   public static final  String QUEUE_01="q_01";
   public static final String QUEUE_02="q_02";
   public static final  String EXCHANGE="exchange2";
   public static final  String ROUDINGKEY1="red";
   public static final  String  ROUDINGKEY2="green";
   @Bean
   public Queue queue01(){
       return  new Queue(QUEUE_01,false);
   }
   @Bean
   public Queue queue02(){
       return new Queue(QUEUE_02,false);
   }
   @Bean
    public DirectExchange exchange2(){
       return new DirectExchange(EXCHANGE);
   }
   @Bean
    public Binding binding01(){
       return BindingBuilder.bind(queue01()).to(exchange2()).with(ROUDINGKEY1);
   }
    @Bean
    public Binding binding02(){
        return BindingBuilder.bind(queue02()).to(exchange2()).with(ROUDINGKEY2);
    }

}
