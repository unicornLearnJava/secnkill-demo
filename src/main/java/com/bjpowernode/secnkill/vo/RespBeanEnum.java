package com.bjpowernode.secnkill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Enumeration;

@ToString
@AllArgsConstructor
@Getter
public enum RespBeanEnum {
    //全局异常
    SUCCESS(200,"登陆成功"),
    ERRORS(500,"用户登录异常"),
    //用户验证异常
    LOGIN_ERROR(50001,"手机号或者密码不能为空"),
    MOBULIE_ERROR(50002,"手机号格式错误"),
    USER_DB(50004,"该手机号不存在"),
    PASS_ERROR(50003,"密码不存在"),
    BING_ERROR(50004,"参数异常"),
    PASS_FAIL(50005,"修改密码异常,手机号或密码错误"),
    UPDADA_PASS_FAIL(50006,"修改密码不成功"),
    USER_NOLOGIN_FAIL(50007,"该用户还没登陆"),

    //数据库查询异常
    STOCK_COUNT_FAIL(60001,"秒杀商品库存不足"),
    REPEAT_FAIL(60002,"一个用户只能抢购一次"),
    //

    //测试秒杀订单
    TEST_FAIL(70004,"测试秒杀订单异常"),


    //非法访问
    CAPTCHE_FAIL(80006,"验证码错误,请重新输入"),
    ILLEGAL_ACCESS(80005,"非法访问"),
    REQUEST_LIMIT_FAIL(80007,"访问过于频繁,请稍后再试");

    ;




    private final Integer code;
    private final String message;

}
