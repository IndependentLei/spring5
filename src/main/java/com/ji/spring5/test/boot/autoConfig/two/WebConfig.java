package com.ji.spring5.test.boot.autoConfig.two;

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

    static class TestBean1{

    }
}
