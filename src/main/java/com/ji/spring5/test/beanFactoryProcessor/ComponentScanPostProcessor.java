package com.ji.spring5.test.beanFactoryProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

// Bean工厂后置后处理器
public class ComponentScanPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            ComponentScan annotation = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
            if(annotation != null) {
                for (String packageName : annotation.basePackages()) {
                    System.out.println(packageName);
                    String path = "classpath*:" + packageName.replace(".", "/") + "/**/*.class";
                    System.out.println("path >>>>>>> " + path);
                    // Metadata 的工厂
                    CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
                    AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
                    for (Resource resource : resources) {
                        MetadataReader reader = factory.getMetadataReader(resource);
                        System.out.println("类名 >>>>>>> " + reader.getClassMetadata().getClassName());
                        System.out.println("是否加了Component注解 >>>>> " + reader.getAnnotationMetadata().hasAnnotation(Component.class.getName()));
                        System.out.println("是否加了Component派生注解 >>>>> " + reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName()));

                        if (reader.getAnnotationMetadata().hasAnnotation(Component.class.getName()) || reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName())) {
                            // 有Component 注解 或 派生注解 @Controller  @Service

                            // 生成BeanDefiniton
                            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(reader.getClassMetadata().getClassName()).getBeanDefinition();
                            if(configurableListableBeanFactory instanceof DefaultListableBeanFactory ){
                                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;
                                // 生成bean的名字
                                String name = generator.generateBeanName(beanDefinition, beanFactory);
                                // 生成bean工厂后处理器
                                beanFactory.registerBeanDefinition(name, beanDefinition);
                            }

                        }
                    }
                }
            }
        }catch (Exception e){

        }
    }
}
