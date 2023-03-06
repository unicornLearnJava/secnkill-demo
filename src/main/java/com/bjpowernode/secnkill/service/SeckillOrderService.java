package com.bjpowernode.secnkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.pojo.SeckillOrder;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
@Service
public interface SeckillOrderService extends IService<SeckillOrder> {

    Order seckill(User user, Integer goodsId);

    String createPath(User user, Integer goodsId);

    boolean checkPath(User user, Integer goodsId, String path);

    Boolean checkCaptche(User user, Integer goodsId, String captche);
}
