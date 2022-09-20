package com.ji.spring5.test.beanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Description :
 * @ClassName : MyBeanPostProcessor
 * @Author : jdl
 * @Create : 2022-09-15 21:32
 */
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            System.out.println("<<<<<<<<<<<< 销毁之前执行，如@PreDestroy");
        }
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            System.out.println("<<<<<<<<<<<< 实例化之前执行，这里返回的对象会替换掉原本的bean");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            System.out.println("<<<<<<<<<<<< 实例化之前执行，这里返回的false会跳过依赖注入阶段");
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            System.out.println("<<<<<<<<<<<< 依赖注入阶段执行，@Autowired，@Value，@Resource");
        }
        return pvs;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            System.out.println("<<<<<<<<<<<< 初始化之前执行，这里返回的对象会替换掉原来的bean，如代理增强，如@PostConstruct、@ConfigurationProperties");
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            System.out.println("<<<<<<<<<<<< 初始化之后执行，这里返回的对象会替换掉原来的bean，如代理增强");
        }
        return bean;
    }
}
