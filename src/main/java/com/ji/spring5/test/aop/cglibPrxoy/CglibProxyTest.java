package com.ji.spring5.test.aop.cglibPrxoy;

public class CglibProxyTest {
    public static void main(String[] args) {
        $Proxy0 proxy = new $Proxy0();
        CglibProxy cglibProxy = new CglibProxy(); // 被代理的类
        proxy.setMethodInterceptor((p,method,objectArgs,mp)->{
            System.out.println("proxy >>>>>>>>> before");
//            return method.invoke(cglibProxy,objectArgs);
//            return mp.invoke(cglibProxy,objectArgs); // 没有使用反射,需要被代理的类
            return mp.invokeSuper(p,objectArgs); // 没有使用反射，需要代理类
        });
        proxy.get();
        System.out.println(proxy.getInt(100));
        System.out.println(proxy.getLong(5100));
        /**
         * proxy >>>>>>>>> before
         * CglibProxy >>>>>>>>>> get
         * proxy >>>>>>>>> before
         * CglibProxy >>>>>>>>>> getInt
         * 100
         * proxy >>>>>>>>> before
         * CglibProxy >>>>>>>>>> getLong
         * 5100
         */
    }
}
