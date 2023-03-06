package com.bjpowernode.secnkill.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.secnkill.config.RedisConfig;
import com.bjpowernode.secnkill.exception.GlobalException;
import com.bjpowernode.secnkill.mapper.UserMapper;
import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.UserService;
import com.bjpowernode.secnkill.utils.CookieUtil;
import com.bjpowernode.secnkill.utils.MD5Util;
import com.bjpowernode.secnkill.utils.UUIDUtil;
import com.bjpowernode.secnkill.vo.LoginVo;
import com.bjpowernode.secnkill.vo.RespBean;
import com.bjpowernode.secnkill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Resource
    private UserMapper userMapper;
   @Resource
    private RedisTemplate redisTemplate;

    @Override
    public RespBean selectById(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        //手机号与密码不能为空
//        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
//            return RespBean.errorss(RespBeanEnum.LOGIN_ERROR);
//        }
//        if(! VaildatorUtil.isMoblie(mobile)){
//             return RespBean.errorss(RespBeanEnum.MOBULIE_ERROR);
//        }
        User user = userMapper.selectById(mobile);
        System.out.println(user);
        if (user == null){
            throw new GlobalException(RespBeanEnum.USER_DB);
        }
        if(!user.getPassword().equals(MD5Util.fromPassToDB(password,"121212"))){
            throw  new GlobalException(RespBeanEnum.PASS_ERROR);
        }
        //生成cookie
        String ticket= UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:"+ticket,user);
        CookieUtil.setCookie(request,response,"usercookie",ticket);
        return RespBean.success(ticket);
    }
    //获取redis中存的cookie
    public User getUserCookie(String userticket,HttpServletRequest request,HttpServletResponse response){
        if (StringUtils.isEmpty(userticket)){
            return null;
        }
        User o = (User)redisTemplate.opsForValue().get("user:" + userticket);
        if (o != null){
            CookieUtil.setCookie(request,response,"usercookie",userticket);
        }
        return o;
    }

    @Override
    public RespBean updataPass(String userticket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserCookie(userticket, request, response);
        if (StringUtils.isEmpty(user)){
          throw new GlobalException(RespBeanEnum.PASS_FAIL);
        }
        //密码567234
        user.setPassword(password);
        int i = userMapper.updateById(user);
        if (i == 1){
            redisTemplate.delete("user:"+userticket);
            return RespBean.success(user);
        }
        return RespBean.errorss(RespBeanEnum.UPDADA_PASS_FAIL);
    }
}
