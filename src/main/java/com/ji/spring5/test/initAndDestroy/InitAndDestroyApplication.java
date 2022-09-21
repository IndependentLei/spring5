package com.ji.spring5.test.initAndDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InitAndDestroyApplication {
    public static void main(String[] args) {
        SpringApplication.run(InitAndDestroyApplication.class,args);
    }

    @Bean(initMethod = "initBean1")
    public Bean1 init(){
        return new Bean1();
    }

    @Bean(destroyMethod = "destroyBean2")
    public Bean2 destroy(){
        return new Bean2();
    }

    /**
     *
     * postConstruct >>>>>>>> 1 初始化
     * InitializingBean >>>>>>>>> 2 初始化
     * initBean1 >>>>>>>>>>> 3 初始化
     *
     ********************************
     *
     * preDestroy 销毁 >>>>>> 2
     * destroy 销毁 >>>>>> 2
     * destroyBean2 销毁 >>>>>>>> 3
     *
     */
}
