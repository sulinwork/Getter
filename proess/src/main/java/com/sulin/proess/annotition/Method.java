package com.sulin.proess.annotition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
    CollectionMethod[] collections() default {};

    InstanceMethod[] instances() default {};
}
