package com.github.resource;

import java.lang.annotation.*;

/**
 * @author tangsong
 * @date 2021/4/17 23:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface DegradeResource {

    String fallback();

    Class<?>[] fallbackClass() default {};

    Class<? extends Throwable>[] exceptionHandle() default {};

}
