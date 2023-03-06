package com.bjpowernode.secnkill.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjpowernode.secnkill.mapper.OrderMapper;
import com.bjpowernode.secnkill.mapper.SeckillGoodsMapper;
import com.bjpowernode.secnkill.mapper.SeckillOrderMapper;
import com.bjpowernode.secnkill.pojo.SeckillGoods;
import com.bjpowernode.secnkill.pojo.SeckillOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestSeckillOrderStockCount {
    @Resource
        private SeckillGoodsMapper seckillGoodsMapper;
    @RequestMapping("/test/count")
    @ResponseBody
    public String test01(){
        SeckillGoods seckillGoods=new SeckillGoods();
        seckillGoods.setStockcount(8);
        UpdateWrapper<SeckillGoods> wrapper=new UpdateWrapper();
        wrapper.gt("stock_count",0).eq("goods_id",1);
        int update = seckillGoodsMapper.update(seckillGoods,wrapper);
        return "asd";

    }
}
