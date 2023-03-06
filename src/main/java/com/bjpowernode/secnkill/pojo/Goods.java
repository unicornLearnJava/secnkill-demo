package com.bjpowernode.secnkill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@TableName("t_goods")
public class Goods implements Serializable {
    private static final long serialVersionUID=2L;
    private Integer id;
    private String goodsname;
    private String goodstitle;
    private String goodsimg;
    private String goodsdetail;
    private BigDecimal goodsprice;
    private Integer goodsstock;
}
