package com.bjpowernode.secnkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.bjpowernode.secnkill.pojo.SeckillOrder;
import com.bjpowernode.secnkill.vo.GoodsVo;

import java.math.BigInteger;

public interface SeckillOrderMapper extends BaseMapper<SeckillOrder> {
    GoodsVo selectGoodsCount(Integer goodsId);
    GoodsVo ifRepeat(BigInteger userId, Integer goodsId);
    void updataGoodsCount(Integer stockCount,BigInteger goodsId);
    void insertSeckillOrder(SeckillOrder seckillOrder);
}
