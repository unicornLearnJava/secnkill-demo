package com.bjpowernode.secnkill.mapper;

import com.bjpowernode.secnkill.pojo.Goods;
import com.bjpowernode.secnkill.vo.GoodsVo;

import java.util.List;

public interface GoodsMapper {
    List<GoodsVo> findGoodsVo();
    GoodsVo findGoodsVoById(Integer id);
}
