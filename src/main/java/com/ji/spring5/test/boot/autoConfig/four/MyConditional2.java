package com.ji.spring5.test.boot.autoConfig.four;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

public class MyConditional2 implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource",null);
    }
}
