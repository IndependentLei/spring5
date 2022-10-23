package com.ji.spring5.test.boot.autoConfig.one;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportSelector.class)  // 导入自动配置类
public class WebConfig {

    @Bean
    public TestBean1 testBean1(){
        return new TestBean1();
    }

//    @Bean
//    public WebConfig1.TestBean2 testBean2(){
//        return new WebConfig1.TestBean2("本项目自动导入bean");
//    }
    static class TestBean1{

    }
}
