package com.bjpowernode.secnkill.controller;

import com.bjpowernode.secnkill.pojo.Goods;
import com.bjpowernode.secnkill.service.Impl.UserServiceImpl;
import com.bjpowernode.secnkill.vo.LoginVo;
import com.bjpowernode.secnkill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.*;
import javax.validation.Valid;

@Controller
@Slf4j
public class HelloController {
    @Resource
    private UserServiceImpl userServiceimpl;
    private RespBean respBean =new RespBean();
    @RequestMapping("/hello")
    public void test01(Model model) {

    }
    //首页
    @RequestMapping("/login")
    public String test02(){
        return "login";
    }
    //登录
    @RequestMapping("/login/doLogin")
    @ResponseBody
    public RespBean text03(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){//@Valid 校验属性
        System.out.println(loginVo.getPassword());
        return userServiceimpl.selectById(loginVo,request,response);
    }

}
