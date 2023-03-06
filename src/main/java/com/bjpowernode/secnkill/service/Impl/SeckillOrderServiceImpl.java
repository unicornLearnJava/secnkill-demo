package com.bjpowernode.secnkill.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.secnkill.mapper.GoodsMapper;
import com.bjpowernode.secnkill.mapper.OrderMapper;
import com.bjpowernode.secnkill.mapper.SeckillGoodsMapper;
import com.bjpowernode.secnkill.mapper.SeckillOrderMapper;
import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.pojo.SeckillGoods;
import com.bjpowernode.secnkill.pojo.SeckillOrder;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.SeckillOrderService;
import com.bjpowernode.secnkill.utils.MD5Util;
import com.bjpowernode.secnkill.utils.UUIDUtil;
import com.bjpowernode.secnkill.vo.GoodsVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service

public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper,SeckillOrder>implements SeckillOrderService {
    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Transactional
    @Override
        public Order seckill(User user, Integer goodsId) {
        GoodsVo goodsVo = goodsMapper.findGoodsVoById(goodsId);
        int stockCount=goodsVo.getStockcount();
        System.out.println(stockCount);

            //秒杀商品库存减一


            UpdateWrapper<SeckillGoods> wrapper=new UpdateWrapper();
            wrapper.setSql("stock_count=stock_count-1").gt("stock_count",0).eq("goods_id",goodsId);
            int update = seckillGoodsMapper.update(null,wrapper);
        if(stockCount <0 && StringUtils.isEmpty(update)){
            redisTemplate.opsForValue().set("isStockEmety",0);
            return  null;
        }

        //订单表
        Order order=new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(new BigInteger("12"));
        order.setGoodsName("IPHONE 12");
        order.setGoodsCount(goodsVo.getStockcount()-1);
        order.setGoodsPrice(goodsVo.getSeckillprice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);

        //秒杀订单表
        SeckillOrder seckillOrder=new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderMapper.insert(seckillOrder);
        redisTemplate.opsForValue().set("order:"+user.getId()+goodsId,order);
        return order;
    }

    @Override
    public String createPath(User user, Integer goodsId) {
        //创建随机值
        String s= UUIDUtil.uuid();
        //进行加密
        String path= MD5Util.md5(s + "1234");
        redisTemplate.opsForValue().set("seckillPath"+user.getId()+goodsId,path,60, TimeUnit.SECONDS);
        return path;
    }

    @Override
    public boolean checkPath(User user, Integer goodsId, String path) {
        if(user ==null || goodsId<0 || StringUtils.isEmpty(path)){
            return  false;
        }
        String o =(String) redisTemplate.opsForValue().get("seckillPath" + user.getId() + goodsId);
        return o.equals(path);
    }

    @Override
    public Boolean checkCaptche(User user, Integer goodsId, String captche) {
        if(user == null||goodsId<0 ||StringUtils.isEmpty(captche) ){
            return false;
        }
        String redisCaptche= (String) redisTemplate.opsForValue().get("captche" + user.getId() + goodsId);
        return redisCaptche.equals(captche);
    }


}
