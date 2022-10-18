package com.ji.spring5.test.boot.runProcess;

import com.ji.spring5.test.beanFactoryProcessor.component.Bean3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class RunProcessApplication {
    public static void main(String[] args) {
        /**
         * 1、演示获取 Bean Definition 源
         * 2、演示推断应用类型
         * 3、演示 ApplicationContext 初始化器
         * 4、演示监听器与事件
         * 5、演示主类推断
         */

        try {
            // 加载源
            SpringApplication application = new SpringApplication(RunProcessApplication.class);
            Set<String> set = new HashSet<>();
            set.add("classpath:classPath.xml");
            application.setSources(set); // bean 的来源


            // 推断应用类型
            // 		this.webApplicationType = WebApplicationType.deduceFromClasspath();
            WebApplicationType webApplicationType = application.getWebApplicationType();
            Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
            deduceFromClasspath.setAccessible(true);
            WebApplicationType invoke =(WebApplicationType) deduceFromClasspath.invoke(webApplicationType);
            System.out.println("invoke --------->"+invoke);

            // ApplicationContext 初始化器
            application.addInitializers(applicationContext -> {
                if(applicationContext instanceof GenericApplicationContext) {
                    GenericApplicationContext context = (GenericApplicationContext) applicationContext;
                    context.registerBean("bean3", Bean3.class);
                }
            });

            // 监听器与事件
            application.addListeners(event -> System.out.println("事件为："+event.getClass()));

            // 主类推断
            Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
            deduceMainApplicationClass.setAccessible(true);

            System.out.println("invoke1 -------> "+deduceMainApplicationClass.invoke(application));

            ConfigurableApplicationContext context = application.run(args);

            for (String beanDefinitionName : context.getBeanDefinitionNames()) {
                String resourceDescription = context.getBeanFactory().getBeanDefinition(beanDefinitionName).getResourceDescription();
                System.out.println(beanDefinitionName + "------------>" +resourceDescription);
            }

            context.close();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public TomcatServletWebServerFactory factory (){
        return new TomcatServletWebServerFactory();
    }
}
