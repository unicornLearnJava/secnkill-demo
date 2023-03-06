package com.bjpowernode.secnkill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
@TableName("t_user")
public class User implements Serializable {
 private static final long serialVersionUID=1L;
 private BigInteger id;
 private String nickname;
 private String password;
 private String salt;
 private String head;
 private Date registerDate;
 private Date lastLoginDate;
 private Integer loginCount;

}
