package com.bjpowernode.secnkill.controller;

import com.bjpowernode.secnkill.mapper.OrderMapper;
import com.bjpowernode.secnkill.mapper.UserMapper;
import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.GoodsService;
import com.bjpowernode.secnkill.service.OrderService;
import com.bjpowernode.secnkill.service.UserService;
import com.bjpowernode.secnkill.vo.GoodsVo;
import com.bjpowernode.secnkill.vo.RespBean;
import com.bjpowernode.secnkill.vo.RespBeanEnum;
import com.bjpowernode.secnkill.vo.SeckillOrderDetailVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SeckillOrderController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderMapper orderMapper;
    @RequestMapping("/seckill/doSeckill")
    @ResponseBody
    public RespBean test01(User user, Integer goodsId){
        if(user == null){
            return RespBean.errorss(RespBeanEnum.LOGIN_ERROR);
        }
        //创建SeckillOrderDetailVo   使秒杀订单页面请求过来能获取相应的数据
        //获取goodsVo
        GoodsVo goodsVo= goodsService.findGoodsVoById(goodsId);
        System.out.println(goodsVo);
        //获取order
        Order order = orderService.selectById(goodsId);
        System.out.println(order);
        SeckillOrderDetailVo detailVo=new SeckillOrderDetailVo();
        detailVo.setGoodsVo(goodsVo);
        detailVo.setOrder(order);
        System.out.println(goodsVo+"   "+order);
        return  RespBean.success(detailVo);
    }
}
