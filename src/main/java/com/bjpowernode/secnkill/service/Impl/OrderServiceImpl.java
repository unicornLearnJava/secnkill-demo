package com.bjpowernode.secnkill.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.secnkill.mapper.OrderMapper;
import com.bjpowernode.secnkill.mapper.SeckillOrderMapper;
import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.service.OrderService;
import com.bjpowernode.secnkill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Override
    public void insertOrder(Order order) {
          orderMapper.insertOrder(order);
    }

    @Override
    public Order selectById(Integer id) {
        return orderMapper.selectById(id);
    }
}
