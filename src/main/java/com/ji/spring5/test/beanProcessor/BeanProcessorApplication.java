package com.ji.spring5.test.beanProcessor;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @Description : bean后置处理器的作用
 * @ClassName : BeanProcessorApplication
 * @Author : jdl
 * @Create : 2022-09-17 21:30
 */
public class BeanProcessorApplication {
    public static void main(String[] args) {
        // GenericApplicationContext 是一个【干净的】容器
        GenericApplicationContext context = new GenericApplicationContext();

        // 用原始的方法注册三个bean
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
        context.registerBean("bean4",Bean4.class);

        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver()); // 处理String的值注入的问题
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class); // 处理 @Autowired,@Value 的后置处理器

        context.registerBean(CommonAnnotationBeanPostProcessor.class); // 处理 @Resource,@PostConstruct，@PreDestroy 的后置处理器

        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());// 处理@ConfigurationProperties 的后置处理器
        // 初始化容器
        context.refresh(); // 执行beanFactory后置处理器，初始化所有单例

        System.out.println("bean4------>:"+context.getBean(Bean4.class).getHome());

        context.close();
    }
}
