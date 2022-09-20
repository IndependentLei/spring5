package com.ji.spring5.test.beanFactoryProcessor;

import com.mysql.cj.util.StringUtils;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.Set;

/**
 * Bean工厂后处理器
 */
public class BeanFactoryProcessorApplication {
    public static void main(String[] args) throws IOException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);

        // Bean工厂后处理器
//        context.registerBean(ConfigurationClassPostProcessor.class); // bean工厂的后处理器 ，处理 @ComponentScan、@Bean、@Import、@ImportResource等注解
//        context.registerBean(MapperScannerConfigurer.class,bd -> {
//            bd.getPropertyValues().add("basePackage","com.ji.spring5.test.beanFactoryProcessor.mapper");
//        }); // @MapperScan

//        ComponentScan annotation = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
//        if(annotation != null){
//            for (String packageName : annotation.basePackages()) {
//                System.out.println(packageName);
//                String path = "classpath*:"+packageName.replace(".", "/")+"/**/*.class";
//                System.out.println("path >>>>>>> "+path);
//                // Metadata 的工厂
//                CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
//                Resource[] resources = context.getResources(path);
//                AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
//                for (Resource resource : resources) {
//                    MetadataReader reader = factory.getMetadataReader(resource);
//                    System.out.println("类名 >>>>>>> "+reader.getClassMetadata().getClassName());
//                    System.out.println("是否加了Component注解 >>>>> "+reader.getAnnotationMetadata().hasAnnotation(Component.class.getName()));
//                    System.out.println("是否加了Component派生注解 >>>>> "+reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName()));
//
//                    if(reader.getAnnotationMetadata().hasAnnotation(Component.class.getName()) || reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName())){
//                        // 有Component 注解 或 派生注解 @Controller  @Service
//
//                        // 生成BeanDefiniton
//                        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(reader.getClassMetadata().getClassName()).getBeanDefinition();
//                        DefaultListableBeanFactory defaultListableBeanFactory = context.getDefaultListableBeanFactory();
//                        // 生成bean的名字
//                        String name = generator.generateBeanName(beanDefinition, defaultListableBeanFactory);
//                        // 生成bean工厂后处理器
//                        context.getDefaultListableBeanFactory().registerBeanDefinition(name,beanDefinition);
//                    }
//                }
//            }
//        }

        context.registerBean(ComponentScanPostProcessor.class);

        System.out.println("-----------------");

        // Metadata 工厂
//        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
//        // 读取Config的Metadata对象
//        MetadataReader metadataReader = factory.getMetadataReader(new ClassPathResource("com/ji/spring5/test/beanFactoryProcessor/Config.class"));
//        // 获取带有@Bean注解的方法
//        Set<MethodMetadata> annotatedMethods = metadataReader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
//        for (MethodMetadata annotatedMethod : annotatedMethods) {
//            System.out.println("annotatedMethod >>>> "+annotatedMethod.getMethodName());
//            String initMethod = annotatedMethod.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();
//            System.out.println("initMethod >>>>> "+initMethod);
//            // 获取beanDefinition
//            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
//            beanDefinitionBuilder.setFactoryMethodOnBean(annotatedMethod.getMethodName(),"config");
//            // 指定自动装配模式
//            beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
//            // @Bean注解中有initMethod的这个方法
//            if(!StringUtils.isNullOrEmpty(initMethod)){
//                beanDefinitionBuilder.setInitMethodName(initMethod);
//            }
//            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
//            // 生成bean
//            context.getDefaultListableBeanFactory().registerBeanDefinition(annotatedMethod.getMethodName(),beanDefinition);
//        }

        // 初始化容器
        context.registerBean(AtBeanPostProcessor.class);

        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        // 关闭容器
        context.close();
    }
}
