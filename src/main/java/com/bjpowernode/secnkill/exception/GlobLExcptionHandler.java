package com.bjpowernode.secnkill.exception;

import com.bjpowernode.secnkill.vo.RespBean;
import com.bjpowernode.secnkill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



//全局异常处理类
@RestControllerAdvice
public class GlobLExcptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if (e instanceof GlobalException){
            GlobalException ex=(GlobalException)e;
            return RespBean.errorss(ex.getRespBeanEnum());
        }else if (e instanceof BindException){
            BindException b=(BindException)e;
            RespBean respBean=RespBean.errorss(RespBeanEnum.BING_ERROR);
            respBean.setMessage("参数异常："+b.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        return RespBean.errorss(RespBeanEnum.ERRORS);
    }
}
