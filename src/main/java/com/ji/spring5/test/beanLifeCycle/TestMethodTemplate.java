package com.ji.spring5.test.beanLifeCycle;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @ClassName : TestMethodTemplate
 * @Author : jdl
 * @Create : 2022-09-15 22:03
 */
public class TestMethodTemplate {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        BeanPostProcessor beanPostProcessor = new BeanPostProcessor() {
            @Override
            public void inject(Object bean) {
                System.out.println("解析@Autowired");
            }
        };
        beanFactory.addBeanPostProcessor(beanPostProcessor);
        Object bean = beanFactory.getBean();
    }

    static class BeanFactory{
        private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();
        public Object getBean(){
            Object bean = new Object();
            System.out.println("构造:"+bean);
            System.out.println("依赖注入:"+bean);
            // 对Bean增强
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.inject(bean);
            }
            System.out.println("初始化:"+bean);
            return bean;
        }

        public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
            beanPostProcessorList.add(beanPostProcessor);
        }
    }

    // 后置处理器
    interface BeanPostProcessor{
        void inject(Object bean);
    }
}
