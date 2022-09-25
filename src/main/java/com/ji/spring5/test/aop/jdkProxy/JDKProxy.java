package com.ji.spring5.test.aop.jdkProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class JDKProxy {
    public static void main(String[] args) {
//        JDKEnhance proxy = new $Proxy0();
//        proxy.get();
        MyProxyInvocationHandler myProxyInvocationHandler = new MyProxyInvocationHandler();
        JDKEnhance proxy = new $Proxy0(myProxyInvocationHandler);
        System.out.println(proxy.get());
        proxy.set();
    }
}

class MyProxyInvocationHandler implements ProxyInvocationHandler{

    @Override
    public Object invoke(Object p,Method method,Object[] args) {

        try {
            System.out.println(">>>>>>>>>>>> before");
            OneJDKEnhance oneJDKEnhance = new OneJDKEnhance();
            Object invoke = method.invoke(oneJDKEnhance, args);
            System.out.println(">>>>>>>>>>>> after");
            return invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

interface ProxyInvocationHandler{
    Object invoke(Object p,Method method,Object[] args);
}

interface JDKEnhance {
    int get();
    void set();
}

class OneJDKEnhance implements JDKEnhance{
    @Override
    public int get() {
        System.out.println("OneEnhance >>>>>>> get");
        return 100;
    }

    @Override
    public void set() {
        System.out.println("OneEnhance >>>>>>> set");
    }
}
