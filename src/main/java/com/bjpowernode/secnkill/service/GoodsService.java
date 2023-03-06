package com.bjpowernode.secnkill.service;

import com.bjpowernode.secnkill.pojo.Goods;
import com.bjpowernode.secnkill.vo.GoodsVo;

import java.util.List;

public interface GoodsService {
    List<GoodsVo> findGoodsVo();
    GoodsVo findGoodsVoById(Integer id);
}
