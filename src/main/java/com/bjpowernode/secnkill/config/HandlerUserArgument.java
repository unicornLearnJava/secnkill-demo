package com.bjpowernode.secnkill.config;

import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.UserService;
import com.bjpowernode.secnkill.utils.CookieUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//处理参数解析器
@Component
public class HandlerUserArgument implements HandlerMethodArgumentResolver {
    @Resource
    private UserService userService;
    //此方法返回true才执行resolveArgument方法
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return parameterType == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

       return UserContext.getUser();
    }
}
