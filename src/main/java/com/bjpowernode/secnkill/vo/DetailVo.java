package com.bjpowernode.secnkill.vo;

import com.bjpowernode.secnkill.pojo.Goods;
import com.bjpowernode.secnkill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//商品页面动态数据
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int seckillStatus;
    private int remainTime;
}
