package com.ji.spring5.test.aop.jdkProxy;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class $Proxy0 implements JDKEnhance{

    private ProxyInvocationHandler handler;

    public $Proxy0(ProxyInvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public int get() {
//        System.out.println(">>>>>>> before");
//        OneJDKEnhance oneJDKEnhance = new OneJDKEnhance();
//        oneJDKEnhance.get();
        try {
            return (int)handler.invoke(this, getMethod,new Object[]{});
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e){
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public void set() {
        try {
            handler.invoke(this, setMethod,new Object[]{});
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e){
            throw new UndeclaredThrowableException(e);
        }
    }

    private static final Method getMethod;
    private static final Method setMethod;
    static {
        try {
            getMethod = JDKEnhance.class.getMethod("get");
            setMethod = JDKEnhance.class.getMethod("set");
        } catch (NoSuchMethodException e) {
            throw new NoSuchFieldError(e.getMessage());
        }
    }
}
