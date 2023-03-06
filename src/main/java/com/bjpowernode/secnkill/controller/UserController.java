package com.bjpowernode.secnkill.controller;

import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//同一用户压测
@Controller
public class UserController {
    @RequestMapping("/user/info")
    @ResponseBody
    public RespBean test01(User user){
        return RespBean.success(user);
    }
}
