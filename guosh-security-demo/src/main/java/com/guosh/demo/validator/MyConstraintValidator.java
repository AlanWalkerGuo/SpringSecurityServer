package com.guosh.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//范型1.验证的注解 2.验证的数据类型
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {

    @Override
    public void initialize(MyConstraint myConstraint) {
        //校验器初始化的规则
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        //校验username如果是tom验证通过
        if (value.equals("tom")){
            return true;
        }else{
            return false;
        }

    }
}
