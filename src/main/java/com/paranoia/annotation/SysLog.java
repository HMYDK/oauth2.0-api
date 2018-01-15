package com.paranoia.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author PARANOIA_ZK
 * @date 2018/1/15 10:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
