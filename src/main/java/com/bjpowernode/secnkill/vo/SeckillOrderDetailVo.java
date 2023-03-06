package com.bjpowernode.secnkill.vo;


import com.bjpowernode.secnkill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrderDetailVo {
    private GoodsVo goodsVo;
    private Order order;
}
