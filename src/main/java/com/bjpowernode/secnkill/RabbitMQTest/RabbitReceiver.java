package com.bjpowernode.secnkill.RabbitMQTest;


import com.bjpowernode.secnkill.pojo.Order;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.GoodsService;
import com.bjpowernode.secnkill.service.SeckillOrderService;
import com.bjpowernode.secnkill.utils.JsonUtil;
import com.bjpowernode.secnkill.vo.GoodsVo;
import com.bjpowernode.secnkill.vo.RespBean;
import com.bjpowernode.secnkill.vo.RespBeanEnum;
import com.bjpowernode.secnkill.vo.SeckillMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RabbitReceiver {
    @Resource
    private GoodsService goodsService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SeckillOrderService seckillOrderService;
    /*
    测试
    @RabbitListener(queues= "queue")
    public void receiver(Object msg){
        log.info("接收到消息:"+msg);
    }

    //faout
    @RabbitListener(queues="queue_01")
    public void receiver1(Object msg){
        log.info("接收到消息01:"+msg);
    }
    //faout
    @RabbitListener(queues="queue_02")
    public void receiver2(Object msg){
        log.info("接收到消息02:"+msg);
    }
    //direct
    @RabbitListener(queues="q_01")
    public void receiver3(Object msg){
        log.info("接收到消息q_01:"+msg);
    }
    //direct
    @RabbitListener(queues="q_02")
    public void receiver4(Object msg){
        log.info("接收到消息q_02:"+msg);
    }

    //topic
    @RabbitListener(queues = "queue_topic01")
    public void reciver4(Object msg){
        log.info("接收到消息"+msg);
    }
    @RabbitListener(queues = "queue_topic02")
    public void reciver5(Object msg){
        log.info("接收到消息"+msg);
    }


    //header
    @RabbitListener(queues = "queue_header01")
    public void reciver6(Message message){
        log.info("获得的消息对象");
        log.info("接收到消息queue_header01:"+new String(message.getBody()));
    }
    @RabbitListener(queues = "queue_header02")
    public void reciver7(Message message){
        log.info("获得的消息对象");
        log.info("接收到消息queue_header02:"+new String(message.getBody()));
    }*/

    //进行下单操作
    @RabbitListener(queues = "seckill_queue")
    public void reciver(String msg){
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        log.info("接收到秒杀消息"+msg);
        User user = seckillMessage.getUser();
        Integer goodsId = seckillMessage.getGoodsId();
        GoodsVo goodsVo = goodsService.findGoodsVoById(goodsId);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断库存
        if(goodsVo.getStockcount() <1){
            return;
        }
        //判断用户是否重复购买
        Order o=(Order) valueOperations.get("order:" + user.getId()+goodsId);
        if ( o !=null){
            return ;
        }
        Order seckill = seckillOrderService.seckill(user, goodsId);
        

    }
}
