package com.bjpowernode.secnkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.vo.RespBean;

import java.io.Serializable;

public interface UserMapper extends BaseMapper<User> {
    User selectById(Serializable id);



}
