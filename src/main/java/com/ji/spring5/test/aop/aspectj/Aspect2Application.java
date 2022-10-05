package com.ji.spring5.test.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Aspect2Application {
    public static void main(String[] args) {

        AspectInstanceFactory factory = new SingletonAspectInstanceFactory(new Aspect());

        List<Advisor> advisorList = new ArrayList<>();
        Method[] methods = Aspect.class.getMethods();
        for (Method method : methods) {
            Before beforeAnnotation = method.getAnnotation(Before.class);
            if(beforeAnnotation != null){
                String value = beforeAnnotation.value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(value);
                AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method,pointcut,factory);

                Advisor advisor= new DefaultPointcutAdvisor(pointcut,advice);
                advisorList.add(advisor);
            }

            After afterAnnotation = method.getAnnotation(After.class);
            if(afterAnnotation != null){
                String value = afterAnnotation.value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(value);
                AspectJAfterAdvice advice = new AspectJAfterAdvice(method,pointcut,factory);

                Advisor advisor= new DefaultPointcutAdvisor(pointcut,advice);
                advisorList.add(advisor);
            }

            AfterReturning afterReturningAnnotation = method.getAnnotation(AfterReturning.class);
            if(afterReturningAnnotation != null){
                String value = afterReturningAnnotation.value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(value);
                AspectJAfterReturningAdvice advice = new AspectJAfterReturningAdvice(method,pointcut,factory);

                Advisor advisor= new DefaultPointcutAdvisor(pointcut,advice);
                advisorList.add(advisor);
            }

            AfterThrowing afterThrowingAnnotation = method.getAnnotation(AfterThrowing.class);
            if(afterThrowingAnnotation != null){
                String value = afterThrowingAnnotation.value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(value);
                AspectJAfterThrowingAdvice advice = new AspectJAfterThrowingAdvice(method,pointcut,factory);

                Advisor advisor= new DefaultPointcutAdvisor(pointcut,advice);
                advisorList.add(advisor);
            }

            Around aroundAnnotation = method.getAnnotation(Around.class);
            if(aroundAnnotation != null){
                String value = aroundAnnotation.value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(value);
                AspectJAroundAdvice advice = new AspectJAroundAdvice(method,pointcut,factory);

                Advisor advisor= new DefaultPointcutAdvisor(pointcut,advice);
                advisorList.add(advisor);
            }
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
        for (Advisor advisor : advisorList) {
            System.out.println(advisor);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Target());
        proxyFactory.addAdvice(ExposeInvocationInterceptor.INSTANCE); // 准备将 MethodInvocation 放入当前线程
        proxyFactory.addAdvisors(advisorList);

        // 统一转换成环绕通知  MethodInterceptor

        /**
         * 其实无论 ProxyFactory 基于那种方式创建代理，最后干活（调用 advice）的是一个MethodInvocation 对象
         *  1、因为 advisor 有多个，且一个套一个调用，因此需要一个调用链对象，即 MethodInvocation
         *  2、MethodInvocation 要知道 advice 有那些，还要知道目标，调用次序如下
         *      将 MethodInvocation 放入线程
         *      |-> before1 -----------------------------
         *      |                                       |
         *      |   |-> before2 -----------------       |
         *      |   |                           |       |
         *      |   |   |-> target ---目标--- advice2  advice1
         *      |   |                           |       |
         *      |   |-> after2 -----------------|       |
         *      |                                       |
         *      |-> after1 -----------------------------|
         *  3、从上图看出，环绕通知才适合 advice ，因此其他 before、afterReturning、afterThrowing 都会被转换成为环绕通知
         *  4、统一转换为环绕通知，体现的是设计模式中的适配器模式
         *      - 对外是为了方便使用要区分 before、afterReturning
         *      - 对内统一都是环绕通知，统一用MethodInterceptor 表示
         */
        try {
            List<Object> methodInterceptorList = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(Target.class.getMethod("foo"), Target.class);
            for (Object o : methodInterceptorList) {
                System.out.println(o);
            }
            /**
             * org.springframework.aop.aspectj.AspectJAfterAdvice: advice method [public void com.ji.spring5.test.aop.aspectj.Aspect2Application$Aspect.after()]; aspect name ''
             * org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor@2f177a4b
             * org.springframework.aop.aspectj.AspectJAroundAdvice: advice method [public void com.ji.spring5.test.aop.aspectj.Aspect2Application$Aspect.around()]; aspect name ''
             * org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor@4278a03f
             * org.springframework.aop.aspectj.AspectJAfterThrowingAdvice: advice method [public void com.ji.spring5.test.aop.aspectj.Aspect2Application$Aspect.afterThrowing()]; aspect name ''
             * org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor@147ed70f
             */

        /**
         * 此步获取所有执行时需要的 advice（静态）
         *  1、即统一转换为 MethodInterceptor 环绕通知，这体现在方法名中的 Interceptors 上
         *  2、适配如下
         *      - MethodBeforeAdviceAdapter 将 AspectJMethodBeforeAdvice 适配为 MethodBeforeAdviceInterceptor
         *      - AfterReturningAdviceAdapter 将 AspectJAfterReturningAdvice 适配为 AfterReturningAdviceInterceptor
         */

            // 创建调用链（环绕通知 + 目标）
            MethodInvocation methodInvocation = new ReflectiveMethodInvocation(null,new Target(),Target.class.getMethod("foo"),new Object[0],Target.class,methodInterceptorList){};
            methodInvocation.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        /**
         * 此步模拟调用链过程，是一个简单的递归过程
         *  1、proceed() 方法调用链中下一个环绕通知
         *  2、每一个环绕通知内部继续调用 proceed()
         *  3、调用到没有更多通知了，就调用目标方法
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
        @After("execution(* foo())")
        public void after(){
            System.out.println("after");
        }
        @Around("execution(* foo())")
        public void around(){
            System.out.println("around");
        }
        @AfterReturning("execution(* foo())")
        public void afterReturning(){
            System.out.println("afterReturning");
        }
        @AfterThrowing("execution(* foo())")
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
