package com.bjpowernode.secnkill.RabbitMQTest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class RabbitController {
    /*
    @Resource
    private RabbitSend rabbitSend;

    @RequestMapping("/testmq")
    @ResponseBody
    public void test01(){
        rabbitSend.send("hello rabbitmq");
    }


    @RequestMapping("/test/faout")
    @ResponseBody
    public void test02(){
        rabbitSend.send1("hello rabbitmq");
    }

    @RequestMapping("/test/direct")
    @ResponseBody
    public void test03(){
        rabbitSend.send2("hello rabbitmq");
        rabbitSend.send3("hello rabbitmq");
    }
    @RequestMapping("/test/topic")
    @ResponseBody
    public void test04(){
        rabbitSend.send5("hello rabbitmq");

    }
    @RequestMapping("/test/header")
    @ResponseBody
    public void test05(){
        rabbitSend.send7("hello rabbitmq");
    }*/
}
