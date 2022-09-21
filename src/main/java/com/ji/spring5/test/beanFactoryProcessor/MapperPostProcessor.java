package com.ji.spring5.test.beanFactoryProcessor;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;

public class MapperPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        try {
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath:com/ji/spring5/test/beanFactoryProcessor/mapper/**/*.class");
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(); // Metadata对象
            AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
            for (Resource resource : resources) {
                System.out.println(resource);
                MetadataReader metadataReader = factory.getMetadataReader(resource);
                boolean isInterface = metadataReader.getClassMetadata().isInterface(); // 是否是接口
                boolean hasFlag = metadataReader.getAnnotationMetadata().hasAnnotation(Mapper.class.getName()); // 有@Mapper注解的
                if(hasFlag && isInterface){
                    //  // 生成BeanDefiniton
                    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MapperFactoryBean.class)
                            .addConstructorArgValue(metadataReader.getClassMetadata().getClassName()) // 构造方式
                            .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE) // 设置自动装配模式
                            .getBeanDefinition();
                    // 生成bean的名字
                    AbstractBeanDefinition bd2 = BeanDefinitionBuilder.genericBeanDefinition(MapperFactoryBean.class)
                            .addConstructorArgValue(metadataReader.getClassMetadata().getClassName()).getBeanDefinition();
                    String beanName = generator.generateBeanName(bd2, registry);
                    registry.registerBeanDefinition(beanName,beanDefinition);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
