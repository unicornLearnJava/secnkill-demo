package com.bjpowernode.secnkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.vo.LoginVo;
import com.bjpowernode.secnkill.vo.RespBean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface UserService  extends IService<User> {
    RespBean selectById(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
    User getUserCookie(String userticket, HttpServletRequest request, HttpServletResponse response);
    RespBean updataPass(String userticket,String password, HttpServletRequest request, HttpServletResponse response);


}
