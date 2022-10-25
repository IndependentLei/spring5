package com.ji.spring5.test.beanFactoryProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.StringUtils;

import java.util.Set;

// Bean工厂后置处理器
public class AtBeanPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            // Metadata 工厂
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
            // 读取Config的Metadata对象
            MetadataReader metadataReader = factory.getMetadataReader(new ClassPathResource("com/ji/spring5/test/beanFactoryProcessor/Config.class"));
            // 获取带有@Bean注解的方法
            Set<MethodMetadata> annotatedMethods = metadataReader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
            for (MethodMetadata annotatedMethod : annotatedMethods) {
                System.out.println("annotatedMethod >>>> " + annotatedMethod.getMethodName());
                String initMethod = annotatedMethod.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();
                System.out.println("initMethod >>>>> " + initMethod);
                // 获取beanDefinition
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
                beanDefinitionBuilder.setFactoryMethodOnBean(annotatedMethod.getMethodName(), "config");
                // 指定自动装配模式
                beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
                // @Bean注解中有initMethod的这个方法
                if (!StringUtils.isEmpty(initMethod)) {
                    beanDefinitionBuilder.setInitMethodName(initMethod);
                }
                AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
                if(configurableListableBeanFactory instanceof DefaultListableBeanFactory) {
                    DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;
                    // 生成bean
                    beanFactory.registerBeanDefinition(annotatedMethod.getMethodName(), beanDefinition);
                }
            }
        }catch (Exception e){

        }
    }
}
