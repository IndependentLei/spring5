package com.ji.spring5.test.boot.factoryBean;

public class FactoryBeanApplication {
    public static void main(String[] args) {
        /**
         * 一个在 Spring 发展阶段中重要，但是目前已经很鸡肋的接口 FactoryBean 的使用要点
         *
         * 一、它的作用是用于制造创建过程中较为复杂的产品，如 SqlSessionFactory ，但是 @Bean 已具备等价功能
         * 二、使用上较为古怪，一不留神就会用错
         *      1、被 FactoryBean 创建的产品
         *          - 会被认为创建、注入依赖、Aware接口回调，前初始化这些都是 FactoryBean 的职责，这些流程都不会走
         *          - 唯有后初始化的流程会走，也就是产品可以被代理增强
         *          - 单调的产品不会存储于 BeanFactory的 singletonObjects 成员中，而是另一个 factoryBeanObjectCache 成员中
         *      2、按名字去获取时，拿的是产品对象，名字前面加 & 获取的是 工厂对象
         */
    }
}
