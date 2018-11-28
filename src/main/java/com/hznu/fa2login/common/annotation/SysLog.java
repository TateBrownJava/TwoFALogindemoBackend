package com.hznu.fa2login.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 *
 * 仅对"method"标注
 * 运行时注解
 * API文档将会对其进行收录
 *
 * @author wheelchen
 * @date 2017-04-07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    /**
     * 模块名称
     *
     * @return
     */
    String modelName() default "";

    /**
     * 操作行为
     *
     * @return
     */
    String operation() default "";
}
