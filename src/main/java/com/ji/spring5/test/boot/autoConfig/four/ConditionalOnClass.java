package com.ji.spring5.test.boot.autoConfig.four;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(MyConditional1.class)
public @interface ConditionalOnClass {
    boolean exist();
    String className();
}
