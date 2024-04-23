package com.pg.backend.common.aop.anno;

import java.lang.annotation.*;

/**
 * @author paul 2024/4/15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno {
    String name() default "paul";
}
