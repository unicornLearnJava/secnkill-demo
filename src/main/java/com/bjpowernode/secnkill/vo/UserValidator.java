package com.bjpowernode.secnkill.vo;

import com.bjpowernode.secnkill.utils.VaildatorUtil;
import com.bjpowernode.secnkill.validations.isMobulie;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//校验属性的规则
public class UserValidator  implements ConstraintValidator<isMobulie,String> {
    private boolean required=false;
    @Override
    public void initialize(isMobulie constraintAnnotation) {
         required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return VaildatorUtil.isMoblie(s);
        }else {
            if(StringUtils.isEmpty(s)){
                return  true;
            }else {
                return  VaildatorUtil.isMoblie(s);
            }
        }
    }
}
