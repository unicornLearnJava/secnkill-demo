package com.bjpowernode.secnkill.controller;


import com.bjpowernode.secnkill.vo.GoodsVo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestRedisTemplateController {
    @Resource
    private RedisTemplate redisTemplate;
    @RequestMapping("/ddd")
    @ResponseBody
    public String Test01(){
        redisTemplate.opsForValue().set("tsetuser","ddddddddd");
        return "success";
    }
    @RequestMapping("/testgoods")
    public void test02(){
        GoodsVo goodsVo=new GoodsVo();

    }
}
