package com.ji.spring5.test.aop.cglibPrxoy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyApplication {
    public static void main(String[] args) {
        MyMethodInterceptor myMethodInterceptor = new MyMethodInterceptor();
        // cglib的代理对象和原本的对象是父子关系
        CglibProxyOne cglibProxy = (CglibProxyOne) Enhancer.create(CglibProxyOne.class, myMethodInterceptor);
        cglibProxy.get();
    }
}


class CglibProxyOne{
    void get(){
        System.out.println("CglibProxy >>>>>>>> ");
    }
}

class MyMethodInterceptor implements MethodInterceptor {
    CglibProxy cglibProxy = new CglibProxy();

    /**
     *
     * @param o 代理对象自己
     * @param method 要执行的方法
     * @param objects 参数
     * @param methodProxy cglib特有的
     * @return 代理对象
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(">>>>>>>>>>> before");
//        Object invoke = method.invoke(cglibProxy, objects); // 使用反射
//        Object invoke = methodProxy.invoke(cglibProxy, objects); // 没有使用反射,需要目标对象   《spring》
        Object invoke = methodProxy.invokeSuper(o, objects);// 没有使用反射，需要代理对象
        System.out.println(">>>>>>>>>>> after");
        return invoke;
    }
}
