package com.ji.spring5.test.annotationUnderStand.autowired;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Configuration
public class AutowiredApplication {
    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredApplication.class);
            DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();


            // 1、根据成员变量的类型注入

            DependencyDescriptor dd1 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"),false);
            System.out.println(beanFactory.doResolveDependency(dd1, "bean1", null, null));
            System.out.println("------------------------------------------------------------->");

            // 2、根据参数的类型注入
            Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
            DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2,0),false);
            System.out.println(beanFactory.doResolveDependency(dd2, "bean1", null, null));
            System.out.println("------------------------------------------------------------->");

            // 3、结果包装为 Optional<Bean2>
            DependencyDescriptor dd3 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean3"),false);
            if(dd3.getDependencyType() == Optional.class){ // 得到的外层的类型
                dd3.increaseNestingLevel();// 取得里面的类型
                Object result = beanFactory.doResolveDependency(dd3, "bean1", null, null);
                System.out.println(result);
                Optional<Object> result1 = Optional.ofNullable(result);
                System.out.println(result1);
            }
            System.out.println("------------------------------------------------------------->");

            // 4、结果包装为 ObjectProvider
            DependencyDescriptor dd4 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean4"),false);
            if(dd4.getDependencyType() == ObjectFactory.class){ // 得到的外层的类型
                dd4.increaseNestingLevel();// 取得里面的类型
                // 调用才去获取
                ObjectFactory<Object> objectFactory = () -> beanFactory.doResolveDependency(dd4, "bean1", null, null);;
                System.out.println(objectFactory.getObject());
            }
            System.out.println("------------------------------------------------------------->");

            // 5、对 @Lazy 的处理
            DependencyDescriptor dd5 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"),false);
            ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
            resolver.setBeanFactory(beanFactory);
            Object bean5 = resolver.getLazyResolutionProxyIfNecessary(dd5, "bean1"); // 得到代理对象
            System.out.println(bean5.getClass());
            System.out.println(bean5);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static class Bean1{
        @Autowired
        @Lazy
        private Bean2 bean2;

        @Autowired
        public void setBean2(Bean2 bean2){
            this.bean2 = bean2;
        }

        @Autowired
        private Optional<Bean2> bean3;

        @Autowired
        private ObjectFactory<Bean2> bean4;

    }

    @Component("bean2")
    static class Bean2{

    }
}
