package com.bjpowernode.secnkill.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.vo.GoodsVo;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends IService<Order> {
    void insertOrder(Order order);
    Order selectById(Integer id);
}
