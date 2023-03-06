package com.bjpowernode.secnkill.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_seckill_goods")
public class SeckillGoods implements Serializable {
    private static final long serialVersionUID=3L;
    private BigInteger id;
    private Integer goodsid;
    private BigDecimal seckillprice;
    @TableField("stock_count")
    private Integer stockcount;
    private Date startDate;
    private Date endDate;
}
