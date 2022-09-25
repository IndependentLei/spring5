package com.ji.spring5.test.aop.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyApplication {
    public static void main(String[] args) {
        MyInvocationHandler handler = new MyInvocationHandler();
        // JDK代理对象和原本的对象是平级关系
        Enhance enhance = (Enhance)Proxy.newProxyInstance(JDKProxyApplication.class.getClassLoader(), new Class[]{Enhance.class}, handler);
        enhance.get();
    }
}

interface Enhance {
    void get();
}

class OneEnhance implements Enhance{
    @Override
    public void get() {
        System.out.println("OneEnhance >>>>>>>");
    }
}

class MyInvocationHandler implements InvocationHandler{
    OneEnhance oneEnhance = new OneEnhance();
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(">>>>>>>>>>>>> before");
        Object invoke = method.invoke(oneEnhance, args);
        System.out.println(">>>>>>>>>>>>> after");
        return invoke;
    }
}
