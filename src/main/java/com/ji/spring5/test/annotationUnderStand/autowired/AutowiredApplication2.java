package com.ji.spring5.test.annotationUnderStand.autowired;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class AutowiredApplication2 {
    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredApplication.class);
            DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

            DependencyDescriptor dd1 = new DependencyDescriptor(MyClass.class.getDeclaredField("serviceArray"),false);
            if (dd1.getDependencyType().isArray()) {
                Class<?> componentType = dd1.getDependencyType().getComponentType();
                System.out.println(componentType);
                String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, componentType);
                List<Object> beanList = new ArrayList<>();
                System.out.println(Arrays.toString(names));
                for (String name : names) {
                    System.out.println(name);
                    Object bean = dd1.resolveCandidate(name, componentType, beanFactory);
                    beanList.add(bean);
                }
                Service[] array =(Service[]) beanFactory.getTypeConverter().convertIfNecessary(beanList, dd1.getDependencyType());
                System.out.println(Arrays.toString(array));
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }



    static class MyClass{
        @Autowired
        private Service[] serviceArray;

    }

    interface Service{

    }

    @Component("service1")
    static class Service1 implements Service{

    }

    @Component("service2")
    static class Service2 implements Service{

    }
}
