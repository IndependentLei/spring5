package com.ji.spring5.test;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @Description : ApplicationContext
 * @ClassName : TestApplicationContext
 * @Author : jdl
 * @Create : 2022-09-14 21:46
 */
public class TestApplicationContext {
    public static void main(String[] args) {
//        testClassPathXmlApplicationContext();
//        testFileSystemXmlApplicationContext();
//        testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebServerApplicationContext();
    }

    // 较为经典的容器，基于classpath下xml格式的配置文件来创建
    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classPath.xml");
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
        /**
         * 首先创建 DefaultListableBeanFactory beanFactory
         * XmlBeanDefinitionReader 在读取bean
         */
//          xml类型 加载过程
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        System.out.println("读取之前");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println("读取之后");

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("classPath.xml"));
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

    // 基于磁盘路径下xml格式的配置文件来创建
    private static void testFileSystemXmlApplicationContext(){
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("D:\\workspace\\UnderCode\\spring\\src\\main\\resources\\classPath.xml");
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 较为经典的容器，基于java配置类来创建
    private static void testAnnotationConfigApplicationContext(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 较为经典的容器，基于java配置类来创建
    private static void testAnnotationConfigServletWebServerApplicationContext(){
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    @Configuration
    static class WebConfig{

        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory(); //Tomcat容器
        }

        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet(); // 视图
        }

        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet servlet){
            return new DispatcherServletRegistrationBean(servlet,"/"); // 将视图注册到Tomcat容器
        }

        @Bean("/hello")
        public Controller controller(){ // 具体的视图
            return (httpServletRequest, httpServletResponse) -> {
                httpServletResponse.getWriter().print("hello");
                return null;
            };
        }

    }

    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

    static class Bean1{

    }

    static class Bean2{
        private Bean1 bean1;

        public void setBean1(Bean1 bean1){
            this.bean1 = bean1;
        }
        public Bean1 getBean1(){
            return bean1;
        }
    }
}
