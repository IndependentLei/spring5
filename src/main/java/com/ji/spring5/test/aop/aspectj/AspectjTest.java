package com.ji.spring5.test.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AspectjTest {
    public static void main(String[] args) {
        // 切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* get())"); // 所有get方法
        // 通知
        MethodInterceptor advice = invocation ->{
            System.out.println("************ before");
            Object object = invocation.proceed(); // 调用目标
            System.out.println("************ after");
            return object;
        };

        // 切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut,advice);

        // 创建代理
        /**
         *  1、proxyTargetClass = false,目标实现了接口，用 jdk 实现
         *  2、proxyTargetClass = false,目标没有实现了接口，用 cglib 实现
         *  3、proxyTargetClass = true ，总是使用 cglib 实现
         */
        Target target = new Target();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target);
        factory.addAdvisor(advisor); // 加入切面
        factory.setInterfaces(target.getClass().getInterfaces()); // 是否实现接口，实现接口用jdk代理，没有设置用cglib代理
        factory.setProxyTargetClass(true); // 总会用cglib实现
        T1 proxy = (T1) factory.getProxy();
        System.out.println("proxy class >>>>>>>>"+proxy.getClass());
        /**
         * class com.ji.spring5.test.aop.aspectj.AspectjTest$Target$$EnhancerBySpringCGLIB$$7d5887d4   cglib代理
         * class com.ji.spring5.test.aop.aspectj.$Proxy0  jdk代理
         * proxy class >>>>>>>>class com.ji.spring5.test.aop.aspectj.AspectjTest$Target$$EnhancerBySpringCGLIB$$18894dd7   cglib代理
         */
        proxy.foo();
        proxy.get();

    }

    static class Target implements T1{
        public void foo(){
            System.out.println("target foo");
        }

        public void get(){
            System.out.println("target get");
        }
    }

    interface T1{
        void foo();
        void get();
    }
}
