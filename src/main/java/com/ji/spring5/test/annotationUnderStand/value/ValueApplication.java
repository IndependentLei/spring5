package com.ji.spring5.test.annotationUnderStand.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Configuration
public class ValueApplication {
    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ValueApplication.class);
            DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

            ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
            resolver.setBeanFactory(beanFactory);

            Field home = Bean1.class.getDeclaredField("home");
            Field age = Bean1.class.getDeclaredField("age");
            Field bean3 = Bean2.class.getDeclaredField("bean3");
            Field bean4Home = Bean4.class.getDeclaredField("home");
//            extracted(context,resolver,home);
//            extracted(context,resolver,age);
//            test2(context,resolver,bean3);
            test2(context,resolver,bean4Home);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    private static void extracted(AnnotationConfigApplicationContext context,ContextAnnotationAutowireCandidateResolver resolver,Field field){
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(field,false); // 属性的描述，是否必须
        String value = resolver.getSuggestedValue(dependencyDescriptor).toString(); // 获取 ${JAVA_HOME}
        System.out.println(value);
        value = context.getEnvironment().resolvePlaceholders(value); // 获取key键值对
        System.out.println(value);

        Integer integer =(Integer) context.getBeanFactory().getTypeConverter().convertIfNecessary(value, dependencyDescriptor.getDependencyType());
        System.out.println(integer.getClass());
    }

    private static void test2(AnnotationConfigApplicationContext context,ContextAnnotationAutowireCandidateResolver resolver,Field field){
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(field,false); // 属性的描述，是否必须
        // 解析 ${}
        String value = resolver.getSuggestedValue(dependencyDescriptor).toString(); // 获取 ${JAVA_HOME}
        System.out.println(value);
        value = context.getEnvironment().resolvePlaceholders(value); // 获取key键值对
        System.out.println(value);

        // 解析 #{}
        Object evaluate = context.getBeanFactory().getBeanExpressionResolver().evaluate(value, new BeanExpressionContext(context.getBeanFactory(), null));
        System.out.println(evaluate.getClass());

        Object result = context.getBeanFactory().getTypeConverter().convertIfNecessary(evaluate, dependencyDescriptor.getDependencyType());
        System.out.println(result.getClass());
        System.out.println(result);
    }

    public class Bean1{
        @Value("${JAVA_HOME}")
        private String home;

        @Value("19")
        private int age;
    }

    public class Bean2{
        @Value("#{@bean3}")
        private Bean3 bean3;
    }


    @Component("bean3")
    public class Bean3{

    }

    public class Bean4{
        @Value("#{'hello'+'${JAVA_HOME}'}")
        private String home;
    }
}
