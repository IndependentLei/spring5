package com.ji.spring5.test.boot.autoConfig.four;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Conditional(MyConditional1.class)  // 条件装配
@ConditionalOnClass(exist = true,className = "com.alibaba.druid.pool.DruidDataSource") // 自定义条件装配注解
public class WebConfig1 {
    @Bean
//    @ConditionalOnMissingBean  // 如果此 bean 存在，就不注入了
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
