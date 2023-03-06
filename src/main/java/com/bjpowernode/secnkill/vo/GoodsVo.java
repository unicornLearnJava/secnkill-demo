package com.bjpowernode.secnkill.vo;


//在页面展示商品信息

import com.bjpowernode.secnkill.pojo.Goods;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods implements Serializable {
    private static final long serialVersionUID=4L;
    private BigDecimal seckillprice;
    private Integer stockcount;
    private Date startDate;
    private Date endDate;
}
