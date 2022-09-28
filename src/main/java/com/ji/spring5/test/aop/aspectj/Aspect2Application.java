package com.ji.spring5.test.aop.aspectj;

import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Aspect2Application {
    public static void main(String[] args) {

        AspectInstanceFactory factory = new SingletonAspectInstanceFactory(new Aspect());

        List<Advisor> advisorList = new ArrayList<>();
        Method[] methods = Aspect.class.getMethods();
        for (Method method : methods) {
            Before annotationsByType = method.getAnnotation(Before.class);
            if(annotationsByType != null){
                String value = annotationsByType.value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(value);
                AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method,pointcut,factory);

                Advisor advisor= new DefaultPointcutAdvisor(pointcut,advice);
                advisorList.add(advisor);
            }
        }
        for (Advisor advisor : advisorList) {
            System.out.println(advisor);
        }

        /**
         * @Before 前置通知会被转换为下面原始的  AspectJMethodBeforeAdvice 形式，该对象包含了如下信息
         *      1、通知代理从哪里来
         *      2、切点是什么
         *      3、通知对象如何创建，本例共用同一个Aspect对象
         * 类似的通知还有
         *      1、AspectJAroundAdvice（环绕通知）
         *      2、AspectJAfterReturningAdvice
         *      3、AspectJAfterThrowingAdvice
         *      4、AspectJAfterAdvice（后置通知）
         */
    }

    static class Aspect{
        @Before("execution(* foo())")
        public void before(){
            System.out.println("before");
        }
        @Before("execution(* foo())")
        public void before1(){
            System.out.println("before");
        }
        public void after(){
            System.out.println("after");
        }
        public void around(){
            System.out.println("around");
        }
        public void afterReturning(){
            System.out.println("afterReturning");
        }
        public void afterThrowing(){
            System.out.println("afterThrowing");
        }
    }

    static class Target{
        public void foo(){
            System.out.println("target foo");
        }
    }
}
