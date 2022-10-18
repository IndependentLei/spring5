package com.ji.spring5.test.boot.runProcess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate;
import org.springframework.test.context.support.DefaultBootstrapContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RunMethodApplication {
    public static void main(String[] args) {
        /**
         * 执行run方法
         * 1、得到 SpringApplicationRunListeners ，名字取得不好，实际是事件发布器
         *      - 发布 Application starting 事件
         * 2、封装启动 args
         * 3、准备 Environment 添加 命令行参数
         * 4、ConfigurationPropertySources 处理、
         *      - 发布 Application environment 已准备
         * 5、通过 EnvironmentPostProcessorApplicationListener 进行 env 后处理
         *      - Application.properties，由StandardConfigDataLocationResolver解析
         *      - Spring.application.json
         * 6、绑定Spring.main到SpringApplication对象
         * 7、打印 banner 图
         * 8、创建容器
         * 9、准备容器
         * 10、加载 bean 定义
         * 11、refresh 容器
         * 12、执行 runner
         *
         */

        try {
            SpringApplication application = new SpringApplication(RunMethodApplication.class);
            application.addListeners(e-> System.out.println(e.getClass())); // 添加事件监听器

            // 获取事件发送器实现类名
            List<String> springApplicationRunListeners = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, RunMethodApplication.class.getClassLoader());
            for (String springApplicationRunListener : springApplicationRunListeners) {
                System.out.println(springApplicationRunListener); // org.springframework.boot.context.event.EventPublishingRunListener
                Class<?> aClass = Class.forName(springApplicationRunListener);
                Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(SpringApplication.class, String[].class);
                EventPublishingRunListener eventPublishingRunListener = (EventPublishingRunListener)declaredConstructor.newInstance(application, args);

                GenericApplicationContext context = new GenericApplicationContext();

                // 启动的各个阶段发送的 发布的消息
                eventPublishingRunListener.starting(); // 表示 boot 程序刚刚开始
                eventPublishingRunListener.environmentPrepared(new StandardEnvironment()); // 环境信息准备完毕
                eventPublishingRunListener.contextPrepared(context); // 在 spring 容器创建并调用初始化器之后，发送此事件
                eventPublishingRunListener.contextLoaded(context); // 所有 bean definition 加载完毕
                context.refresh();
                eventPublishingRunListener.started(context);  // spring容器 初始化完成（refresh 方法调用完毕）
                eventPublishingRunListener.running(context); // 表示 启动完毕

                eventPublishingRunListener.failed(context,new Exception("出错误了")); // 启动过程中出现错误
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
