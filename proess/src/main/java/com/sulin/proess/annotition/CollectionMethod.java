package com.sulin.proess.annotition;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CollectionMethod {
    String path();

    String action() default "";

    String targetField();


}
