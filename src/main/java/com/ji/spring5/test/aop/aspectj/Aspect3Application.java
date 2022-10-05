package com.ji.spring5.test.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Aspect3Application {
    public static void main(String[] args) {
        try {
            Target target = new Target();
            List<MethodInterceptor> methodInterceptorList =Arrays.asList(
                    new Advice1(), new Advice2()
            );
            MyInvocation invocation = new MyInvocation(target, Target.class.getMethod("foo"), new Object[0], methodInterceptorList);
            invocation.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static class Target{
        public void foo(){
            System.out.println("Target foo()");
        }
    }

    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Advice1.before()");
            Object object = invocation.proceed();
            System.out.println("Advice1.after()");
            return object;
        }
    }

    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Advice2.before()");
            Object object = invocation.proceed();
            System.out.println("Advice2.after()");
            return object;
        }
    }

    // 责任链模式
    static class MyInvocation implements MethodInvocation {
        private Method method;
        private Object[] args;
        private Object target;
        private List<MethodInterceptor> methodInterceptorList;

        private int count = 1;

        public  MyInvocation(Object target,Method method,Object[] args,List<MethodInterceptor> methodInterceptorList){
            this.target = target;
            this.methodInterceptorList = methodInterceptorList;
            this.method = method;
            this.args = args;
        }

        @Override
        public Method getMethod() {
            return this.method;
        }

        @Override
        public Object[] getArguments() {
            return this.args;
        }

        @Override
        public Object proceed() throws Throwable {
            if(count > methodInterceptorList.size()){
                return method.invoke(target,args);
            }
            MethodInterceptor methodInterceptor = methodInterceptorList.get(count++-1);
            return methodInterceptor.invoke(this);
        }

        @Override
        public Object getThis() {
            return this;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return null;
        }
    }
}
