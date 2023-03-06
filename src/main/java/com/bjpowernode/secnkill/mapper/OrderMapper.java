package com.bjpowernode.secnkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.vo.GoodsVo;

public interface OrderMapper extends BaseMapper<Order> {
    void insertOrder(Order order);
    Order selectById(Integer id);
}
