package com.ji.spring5.test.lazyAndProxy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.ji.spring5.test.lazyAndProxy")
public class ProxyApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProxyApplication.class);
        E bean = context.getBean(E.class);
        System.out.println(">>>>>>>>>>>> "+bean.getF().getClass());
        System.out.println(">>>>>>>>>>>> "+bean.getF());
        System.out.println(">>>>>>>>>>>> "+bean.getF());
        System.out.println(">>>>>>>>>>>> "+bean.getF());

        System.out.println(">>>>>>>>>>>> "+bean.getG().getClass());
        System.out.println(">>>>>>>>>>>> "+bean.getG());
        System.out.println(">>>>>>>>>>>> "+bean.getG());
        System.out.println(">>>>>>>>>>>> "+bean.getG());


        System.out.println(">>>>>>>>>>>> "+bean.getH().getClass());
        System.out.println(">>>>>>>>>>>> "+bean.getH());
        System.out.println(">>>>>>>>>>>> "+bean.getH());
        System.out.println(">>>>>>>>>>>> "+bean.getH());


        System.out.println(">>>>>>>>>>>> "+bean.getI().getClass());
        System.out.println(">>>>>>>>>>>> "+bean.getI());
        System.out.println(">>>>>>>>>>>> "+bean.getI());
        System.out.println(">>>>>>>>>>>> "+bean.getI());

        /**
         * 加上@Lazy注解每次获取的都是代理对象
         >>>>>>>>>>>> class com.ji.spring5.test.lazyAndProxy.F$$EnhancerBySpringCGLIB$$4015b356
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.F@266374ef
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.F@24c4ddae
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.F@a82c5f1

         在类上面加上代理模式
         >>>>>>>>>>>> class com.ji.spring5.test.lazyAndProxy.G$$EnhancerBySpringCGLIB$$20ae60e9
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.G@4a3631f8
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.G@6b58b9e9
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.G@f14a7d4

         // 使用ObjectFactory<H>
         >>>>>>>>>>>> class com.ji.spring5.test.lazyAndProxy.H
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.H@79c97cb
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.H@42a15bdc
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.H@27e47833

         // 使用ApplicationContext
         >>>>>>>>>>>> class com.ji.spring5.test.lazyAndProxy.I
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.I@6f6745d6
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.I@27508c5d
         >>>>>>>>>>>> com.ji.spring5.test.lazyAndProxy.I@4f704591
         */
    }
}
