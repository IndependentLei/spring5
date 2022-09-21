package com.ji.spring5.test.awareAndInitializingBean;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * aware接口 和 InitializingBean接口
 */
public class AwareAndInitializingBean {
    public static void main(String[] args) {
        /**
         * 一、Aware 接口用于注入一些与容器相关信息，例如
         *      1、BeanNameAware 注入 bean的名字
         *      2、BeanFactoryAware 注入 BeanFactory 容器
         *      3、ApplicationContextAware 注入 ApplicationContext 容器
         *      4、EmbeddedValuesResolverAware ${}
         */

        GenericApplicationContext context = new GenericApplicationContext();
//        context.registerBean("myBean",MyBean.class);

        context.registerBean("myConfig1", MyConfig1.class);
        context.registerBean("myConfig2", MyConfig2.class);
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        /**
         *  二、 2、3、4的功能有@Autowired就能实现啊，为啥还要用Aware接口呢
         *  简单说：      1、@Autowired的解析需要用到bean的后置处理器，属于扩展功能
         *              2、而Aware接口属于内置功能，不加任何扩展，Spring就能识别某些情况下，扩展功能就会失效，而内置功能不会失效
         *
         *              例子1、你会发现用Aware注入ApplicationContext成功，而@AutoWired注入ApplicationContext失败
         */

        context.refresh(); // 1、beanFactory 2、
        context.close();

        /**
         * 三、Java配置类在添加了bean工厂后置处理器
         *      你会发现用传统接口方式的注入和初始化任然成，而@Autowired 和@PostConstruct 的注入和初始化失败
         */


        /**
         * 四、总结
         *      1、Aware 接口提供了一种【内置】的注入手段，剋以注入BeanFactory、ApplicationContext
         *      2、InitializingBean 接口提供了一种【内置】的初始化手段
         *      3、内置的注入和初始化不受扩展功能的影响，总会被执行，因此Spring框架内部的类常用他们
         */
    }
}
