package com.bjpowernode.secnkill.config;

import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.UserService;
import com.bjpowernode.secnkill.utils.CookieUtil;
import com.bjpowernode.secnkill.vo.RespBean;
import com.bjpowernode.secnkill.vo.RespBeanEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


//接口限流拦截器
@Component
public class RequestLimitInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm=(HandlerMethod)handler;
            //获取用户
            User user = getUser(request, response);
            UserContext.setUser(user);
            RequestLimit requestLimit = hm.getMethodAnnotation(RequestLimit.class);
            if(requestLimit == null){
                return true;
            }
            int second = requestLimit.second();
            int maxCount = requestLimit.maxCount();
            boolean b = requestLimit.needLogin();
            String key=request.getRequestURI();
            if(b){
                if (user == null){
                    rander(response,RespBeanEnum.USER_NOLOGIN_FAIL);
                    return false;
                }
                key +=user.getId();
                System.out.println("requestUri:"+key);
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer requestCount=(Integer) valueOperations.get(key);
            if(requestCount == null){
                valueOperations.set(key,1,second, TimeUnit.SECONDS);
            }else if(requestCount <maxCount){
                valueOperations.increment(key);
            }else {
                rander(response,RespBeanEnum.REQUEST_LIMIT_FAIL);
                return false;
            }
        }
        return true;
    }

    private void rander(HttpServletResponse response, RespBeanEnum userNologinFail) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        RespBean eror=RespBean.errorss(userNologinFail);
        writer.write(new ObjectMapper().writeValueAsString(eror));
        writer.flush();
        writer.close();
    }

    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getCookieValue(request, "usercookie");
        if (ticket == null){
            return  null;
        }
        return userService.getUserCookie(ticket,request,response);
    }
}
