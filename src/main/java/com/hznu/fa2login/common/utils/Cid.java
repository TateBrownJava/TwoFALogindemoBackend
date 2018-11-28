package com.hznu.fa2login.common.utils;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: TateBrown
 * @date: 2018/8/2 15:52
 * @param:
 * @return:
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(regexp = "(^[1-9]\\d{5}(19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)")
@Null
@Length(min=0,max=0)
@Documented
@Constraint(validatedBy = {})
@Target({METHOD,FIELD,CONSTRUCTOR,PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Cid{
    String message() default "身份证号格式错误";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
