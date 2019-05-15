package com.guosh.demo.validator;

//自定义MyConstraint注解

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//作用在字段跟方法上面
@Target({ElementType.FIELD,ElementType.METHOD})
//运行时注解
@Retention(RetentionPolicy.RUNTIME)
//需要校验注解的类
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {
    String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
