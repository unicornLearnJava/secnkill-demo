package com.bjpowernode.secnkill.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order")
public class Order implements Serializable {

    private BigInteger id;
    private BigInteger userId;
    private Integer goodsId;
    private BigInteger deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private BigDecimal goodsPrice;
    private  int orderChannel;
    private int status;
    private Date createDate;
    private Date payDate;

}
