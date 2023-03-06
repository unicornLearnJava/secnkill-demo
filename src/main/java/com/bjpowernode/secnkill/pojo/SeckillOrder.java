package com.bjpowernode.secnkill.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@TableName("t_seckill_order")
public class SeckillOrder implements Serializable {
   @TableId
    private BigInteger id;
    @TableField("user_id")
    private BigInteger userId;
    @TableField("order_id")
    private BigInteger orderId;
    @TableField("goods_id")
    private Integer goodsId;
}
