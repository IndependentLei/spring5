package com.ji.spring5.test.initAndDestroy;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

public class Bean1 implements InitializingBean {
    // 三种初始化bean的方式 顺序为 PostConstruct  afterPropertiesSet  initBean1

    @PostConstruct
    public void postConstruct(){
        System.out.println("postConstruct >>>>>>>> 1 初始化");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean >>>>>>>>> 2 初始化");
    }

    public void initBean1(){
        System.out.println("initBean1 >>>>>>>>>>> 3 初始化");
    }
}
