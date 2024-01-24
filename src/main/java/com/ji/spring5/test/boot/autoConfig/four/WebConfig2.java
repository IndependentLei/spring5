package com.ji.spring5.test.boot.autoConfig.four;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
@ConditionalOnClass(exist = false,className = "com.alibaba.druid.pool.DruidDataSource") // 自定义条件装配注解
public class WebConfig2 {
    @Bean
//    @ConditionalOnMissingBean  // 如果此 bean 存在，就不注入了
    public TestBean3 testBean3(){
        return new TestBean3("第三方自动导入bean");
    }
    static class TestBean3{
        private String name;

        public TestBean3(String name){
            this.name = name;
        }

        @Override
        @Transactional
        public String toString() {
            return "TestBean3{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        System.out.println(1);
        throw new Error("异常了");
    }
}
