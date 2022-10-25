package com.ji.spring5.test.boot.autoConfig.four;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

public class MyConditional1 implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> conditionalOnClass = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());
        String className = (String)conditionalOnClass.get("className");
        Boolean exist =(Boolean) conditionalOnClass.get("exist");
        return exist == ClassUtils.isPresent(className,null);
    }
}
