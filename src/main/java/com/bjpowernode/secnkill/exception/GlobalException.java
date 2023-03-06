package com.bjpowernode.secnkill.exception;

import com.bjpowernode.secnkill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//全局异常
public class GlobalException extends RuntimeException {
    private RespBeanEnum respBeanEnum;
}
