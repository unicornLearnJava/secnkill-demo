package com.bjpowernode.secnkill.service.Impl;

import com.bjpowernode.secnkill.mapper.GoodsMapper;
import com.bjpowernode.secnkill.service.GoodsService;
import com.bjpowernode.secnkill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoById(Integer id) {
        return goodsMapper.findGoodsVoById(id);
    }
}
