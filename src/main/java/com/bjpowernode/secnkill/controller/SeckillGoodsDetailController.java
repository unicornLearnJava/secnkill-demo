package com.bjpowernode.secnkill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjpowernode.secnkill.RabbitMQTest.RabbitSend;
import com.bjpowernode.secnkill.config.RequestLimit;
import com.bjpowernode.secnkill.mapper.SeckillOrderMapper;
import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.pojo.SeckillOrder;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.GoodsService;
import com.bjpowernode.secnkill.service.Impl.SeckillOrderServiceImpl;
import com.bjpowernode.secnkill.utils.JsonUtil;
import com.bjpowernode.secnkill.vo.GoodsVo;
import com.bjpowernode.secnkill.vo.RespBean;
import com.bjpowernode.secnkill.vo.RespBeanEnum;
import com.bjpowernode.secnkill.vo.SeckillMessage;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class SeckillGoodsDetailController implements InitializingBean{

    @Resource
    private GoodsService goodsService;
    @Resource
    private SeckillOrderServiceImpl seckillOrderService;
    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RabbitSend rabbitSend;
    //添加内存标记
    private   Map<Integer,Boolean> emptyStockMap=new HashMap();



//    @RequestMapping("/goods/seckillOrder")
//    public String test01(Model model,User user, Integer goodsId){
//        if (user == null){
//            return "login";
//        }
//        //判断订单库存
//        GoodsVo goodsVo= goodsService.findGoodsVoById(goodsId);
//
//        if (goodsVo.getStockcount() < 1){
//            model.addAttribute("srrmes",RespBean.errorss(RespBeanEnum.STOCK_COUNT));
//            return "secKillFail";
//        }
//        //判断用户是否重复购买
//        GoodsVo goodsVo1 = seckillOrderService.ifRepeat(user.getId(), goodsId);
//        if ( goodsVo1 !=null){
//            model.addAttribute("srrmes",RespBean.errorss(RespBeanEnum.REPEAT_ERRORS,user));
//            return "secKillFail";
//        }
//
//        //订单表
//        Order order=new Order();
//        order.setUserId(user.getId());
//        order.setGoodsId(goodsVo.getId());
//        order.setDeliveryAddrId(new BigInteger("12"));
//        order.setGoodsName("IPHONE 12");
//        order.setGoodsCount(goodsVo.getStockcount()-1);
//        order.setGoodsPrice(goodsVo.getSeckillprice());
//        order.setOrderChannel(1);
//        order.setStatus(0);
//        order.setCreateDate(new Date());
//        orderService.insertOrder(order);
//        //获取orderid
//
//
//        //秒杀订单表
//        SeckillOrder seckillOrder=new SeckillOrder();
//        seckillOrder.setUserId(user.getId());
//        seckillOrder.setOrderId(order.getId());
//        seckillOrder.setGoodsId(goodsVo.getId());
//        seckillOrderService.insertSeckillOrder(seckillOrder);
//        //秒杀商品库存减一
//        seckillOrderService.updataGoodsCount(goodsVo.getStockcount()-1,goodsVo.getId());
//        model.addAttribute("goods",goodsVo);
//        model.addAttribute("user",user);
//        model.addAttribute("order",order);
//        return "orderDetail";
//    }

    //前后端分离之后
    @RequestMapping("/goods/{path}/seckillOrder")
    @ResponseBody
    public RespBean test02(Model model,User user, Integer goodsId,@PathVariable(value = "path") String path) {
        if (user == null){
            return RespBean.errorss(RespBeanEnum.USER_NOLOGIN_FAIL);
        }
        //判断隐藏的秒杀接口路径是否正确
        boolean result=seckillOrderService.checkPath(user,goodsId,path);
        if (! result){
            return RespBean.errorss(RespBeanEnum.ILLEGAL_ACCESS);
        }
        /*
        //判断订单库存
        GoodsVo goodsVo= goodsService.findGoodsVoById(goodsId);

        if (goodsVo.getStockcount() < 1){

            return RespBean.errorss(RespBeanEnum.STOCK_COUNT_FAIL);
        }
        //判断用户是否重复购买
        Order o=(Order) redisTemplate.opsForValue().get("order:" + user.getId()+goodsId);
        if ( o !=null){
            return RespBean.errorss(RespBeanEnum.REPEAT_FAIL);
        }

        Order order = seckillOrderService.seckill(user, goodsId);
        */
        //服务优化后
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if(emptyStockMap.get(goodsId)){
            return RespBean.errorss(RespBeanEnum.STOCK_COUNT_FAIL);
        }
        //判断用户是否重复购买
        Order o=(Order) valueOperations.get("order:" + user.getId()+goodsId);
        if ( o !=null){
            return RespBean.errorss(RespBeanEnum.REPEAT_FAIL);
        }
        //预减库存
        Long decrement = valueOperations.decrement("stock_count:"+ goodsId);
        if(decrement <0 ){
            emptyStockMap.put(goodsId,true);
            valueOperations.increment("stock_count:"+goodsId);
            return RespBean.errorss(RespBeanEnum.STOCK_COUNT_FAIL);
        }
        SeckillMessage seckillMessage=new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(goodsId);
        rabbitSend.seckillSend(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.success(0);
    }

    //获取秒杀结果   order有值表示秒杀成功  -1 失败  0 排队中
    @RequestMapping(value = "/seckill/getResult",method = RequestMethod.GET)
    @ResponseBody
    public RespBean  test03(User user,Integer goodsId){
        //获取数据库秒杀订单
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId()).eq("goods_id", goodsId));
        System.out.println(seckillOrder.getOrderId());
        if(seckillOrder != null){
            return RespBean.success(seckillOrder.getOrderId());
        }else if (redisTemplate.hasKey("isStockEmety")){
            return  RespBean.success(-1);
        }
        return RespBean.success(0);
    }

    //隐藏秒杀地址
    //接口限流规则   5秒 5个请求
    @RequestLimit(second=5,maxCount=5,needLogin=true)
    @RequestMapping(value ="/seckill/getPath",method = RequestMethod.GET)
    @ResponseBody
    public RespBean test04(User user,Integer goodsId,String captche){
        if (user == null){
            return RespBean.errorss(RespBeanEnum.USER_NOLOGIN_FAIL);
        }
        //接口限流
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer count=(Integer) valueOperations.get("request_limit"+user.getId());
        if (count ==null){
            valueOperations.set("request_limit"+user.getId(),1,5,TimeUnit.SECONDS);
        }else if(count <5){
            valueOperations.increment("request_limit"+user.getId());
        }else {
            return RespBean.errorss(RespBeanEnum.REQUEST_LIMIT_FAIL);
        }
        String path=seckillOrderService.createPath(user,goodsId);
        Boolean o=seckillOrderService.checkCaptche(user,goodsId,captche);
        if (! o){
            return RespBean.errorss(RespBeanEnum.CAPTCHE_FAIL);
        }
        return RespBean.success(path);
    }


    //生成验证码
    @RequestMapping(value = "captche",method = RequestMethod.GET)

    public void test4(User user, Integer goodsId, HttpServletResponse response){
        //设置请求头为输出图片类型
        response.setContentType("image/jpg");
        response.setHeader("Pargam","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        //生成验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 32, 3);
        System.out.println("生成验证码goodsId:"+goodsId);
        redisTemplate.opsForValue().set("captche"+user.getId()+goodsId,captcha.text(),300, TimeUnit.SECONDS);
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
           log.info("验证码生成失败",e.getMessage());
        }


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVo = goodsService.findGoodsVo();
        if(CollectionUtils.isEmpty(goodsVo)){
            return;
        }
        goodsVo.forEach(goodsVoS -> {
            redisTemplate.opsForValue().set("stock_count:"+goodsVoS.getId(), goodsVoS.getStockcount());
                    emptyStockMap.put(goodsVoS.getId(), false);
        });
    }
}
