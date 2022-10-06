package com.ji.spring5.test.dispatcherServlet.init;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan
public class WebConfig {
    // 内嵌 web 容器工厂
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }

    // 创建DispatcherServlet
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }

    // 注册 DispatcherServlet ，Spring MVC 的入口
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet){
        DispatcherServletRegistrationBean dispatcherServletRegistrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        dispatcherServletRegistrationBean.setLoadOnStartup(1); // 程序启动就初始化 DispatcherServlet
        return dispatcherServletRegistrationBean;
    }

    // 以下为 @EnableWebMvc 导入的 Bean (注意不加 @EnableWebMvc DispatcherServlet 初始化时还是会添加默认的组件，但并不作为 bean，给测试带来困扰)

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public MyRequestMappingHandlerAdapter requestMappingHandlerAdapter(){
        return new MyRequestMappingHandlerAdapter();
    }
}

