package com.ji.spring5.test.initAndDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;

public class Bean2 implements DisposableBean {

    // 三种bean的销毁方式 顺序为  preDestroy   destroy   destroyBean2

    @PreDestroy
    public void preDestroy(){
        System.out.println("preDestroy 销毁 >>>>>> 1");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy 销毁 >>>>>> 2");
    }

    public void destroyBean2(){
        System.out.println("destroyBean2 销毁 >>>>>>>> 3");
    }
}
