package com.ji.spring5.test.aop.cglibPrxoy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class $Proxy0 extends CglibProxy{
    @Override
    public int getInt(int i) {
        try {
            return (int)methodInterceptor.intercept(this,getInt,new Object[]{i},getIntMethod);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getLong(long i) {
        try {
            return (long)methodInterceptor.intercept(this,getLong,new Object[]{i},getLongMethod);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private MethodInterceptor methodInterceptor;

    public void setMethodInterceptor(MethodInterceptor methodInterceptor){
        this.methodInterceptor = methodInterceptor;
    }
    @Override
    public void get() {
        try {
            methodInterceptor.intercept(this,get,null,getMethod);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void getSuper(){
        super.get();
    }

    public int getSuperInt(int i){
        return super.getInt(i);
    }

    public long getSuperLong(long j){
        return super.getLong(j);
    }


    private static final Method get;
    private static final Method getInt;
    private static final Method getLong;

    private static final MethodProxy getMethod;
    private static final MethodProxy getIntMethod;
    private static final MethodProxy getLongMethod;

    static {
        try {
            get = CglibProxy.class.getDeclaredMethod("get");
            getInt = CglibProxy.class.getDeclaredMethod("getInt",int.class);
            getLong = CglibProxy.class.getDeclaredMethod("getLong",long.class);
            getMethod = MethodProxy.create(CglibProxy.class, $Proxy0.class,"()V","get","getSuper");
            getIntMethod = MethodProxy.create(CglibProxy.class, $Proxy0.class,"(I)I","getInt","getSuperInt");
            getLongMethod = MethodProxy.create(CglibProxy.class, $Proxy0.class,"(J)J","getLong","getSuperLong");
            // desc ---> (参数类型)返回值类型
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
