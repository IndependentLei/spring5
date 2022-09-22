package com.ji.spring5.test.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScopeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScopeApplication.class,args);
    }

    /**
     *   singleton 、prototype 、request 、session 、application
     *   --------------------------------------------------
     *   演示request、session、application作用域
     *   打开不同的浏览器，刷新http:localhost:8080/test
     */
}
