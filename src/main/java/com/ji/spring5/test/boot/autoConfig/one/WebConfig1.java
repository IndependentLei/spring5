package com.ji.spring5.test.boot.autoConfig.one;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig1 {
    @Bean
    @ConditionalOnMissingBean  // 如果此 bean 存在，就不注入了
    public TestBean2 testBean2(){
        return new TestBean2("第三方自动导入bean");
    }
    static class TestBean2{
        private String name;

        public TestBean2(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestBean2{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
