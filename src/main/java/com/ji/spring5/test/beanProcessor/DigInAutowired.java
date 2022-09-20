package com.ji.spring5.test.beanProcessor;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description : AutowiredAnnotationBeanPostProcessor 运行分析
 * @ClassName : DigInAutowired
 * @Author : jdl
 * @Create : 2022-09-17 21:31
 */
public class DigInAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Bean3 bean33 = new Bean3();
        System.out.println("bean33 >>>>:"+bean33);
        Bean2 bean22 = new Bean2();
        System.out.println("bean22 >>>>:"+bean22);
        beanFactory.registerSingleton("bean2",bean22); // 注入的位成品 bean ，不会在对依赖注入，初始化
        beanFactory.registerSingleton("bean3",bean33);
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver()); // 处理@Value的处理器

        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders); // ${}的解析器


        // 1、查找那些属性、方法加了@Autowired，这成为位InjectionMetadata
        AutowiredAnnotationBeanPostProcessor postProcessor = new AutowiredAnnotationBeanPostProcessor();
        postProcessor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        System.out.println("bean1--->"+bean1);
//        postProcessor.postProcessProperties(null,bean1,"bean1"); // 执行依赖注入 @Autowired @Value
//        System.out.println("bean1 >>>> "+bean1);

        // 2、调用InjectionMetadata 来进行依赖注入，注入时按类型查找值
        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);
        InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(postProcessor,"bean1",Bean1.class,null);
        System.out.println("metadata >>>> "+metadata);

        metadata.inject(bean1,"bean1",null);
        System.out.println("bean1 >>>> "+bean1);


        // 3、如何按类型查找值

        // 给属性注入Spring 的 Bean
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        bean3.setAccessible(true);
        DependencyDescriptor dd1 = new DependencyDescriptor(bean3,false); // 属性对象,是否必须
        Object invokeBean3 = beanFactory.doResolveDependency(dd1, null, null, null);
        System.out.println("invokeBean3 >>>> "+invokeBean3);

        // 标注的方法 输入Spring 的 Bean
        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2,0),false);
        Object invokeSetBean2 = beanFactory.doResolveDependency(dd2, null, null, null);
        System.out.println("invokeSetBean2 >>>> "+invokeSetBean2);

        Method setHome = Bean1.class.getDeclaredMethod("setHome", String.class);
        DependencyDescriptor dd3 = new DependencyDescriptor(new MethodParameter(setHome,0),true);
        Object invokeSetHome = beanFactory.doResolveDependency(dd3, null, null, null);
        System.out.println("invokeSetHome >>>> "+invokeSetHome);

        /**
         * D:\environment\jdk1.8.0_241\jdk1.8.0_241\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:60151,suspend=y,server=n -javaagent:C:\Users\12651\AppData\Local\JetBrains\IntelliJIdea2020.2\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\charsets.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\deploy.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\access-bridge-64.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\cldrdata.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\dnsns.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\jaccess.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\jfxrt.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\localedata.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\nashorn.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\sunec.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\sunjce_provider.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\sunmscapi.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\sunpkcs11.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\ext\zipfs.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\javaws.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\jce.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\jfr.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\jfxswt.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\jsse.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\management-agent.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\plugin.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\resources.jar;D:\environment\jdk1.8.0_241\jdk1.8.0_241\jre\lib\rt.jar;D:\workspace\UnderCode\spring\target\classes;D:\environment\Maven\Repository\org\springframework\boot\spring-boot-starter\2.3.7.RELEASE\spring-boot-starter-2.3.7.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\boot\spring-boot\2.3.7.RELEASE\spring-boot-2.3.7.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\spring-context\5.2.12.RELEASE\spring-context-5.2.12.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\boot\spring-boot-autoconfigure\2.3.7.RELEASE\spring-boot-autoconfigure-2.3.7.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\boot\spring-boot-starter-logging\2.3.7.RELEASE\spring-boot-starter-logging-2.3.7.RELEASE.jar;D:\environment\Maven\Repository\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;D:\environment\Maven\Repository\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;D:\environment\Maven\Repository\org\apache\logging\log4j\log4j-to-slf4j\2.13.3\log4j-to-slf4j-2.13.3.jar;D:\environment\Maven\Repository\org\apache\logging\log4j\log4j-api\2.13.3\log4j-api-2.13.3.jar;D:\environment\Maven\Repository\org\slf4j\jul-to-slf4j\1.7.30\jul-to-slf4j-1.7.30.jar;D:\environment\Maven\Repository\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;D:\environment\Maven\Repository\org\springframework\spring-core\5.2.12.RELEASE\spring-core-5.2.12.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\spring-jcl\5.2.12.RELEASE\spring-jcl-5.2.12.RELEASE.jar;D:\environment\Maven\Repository\org\yaml\snakeyaml\1.26\snakeyaml-1.26.jar;D:\environment\Maven\Repository\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar;D:\environment\Maven\Repository\org\springframework\boot\spring-boot-starter-web\2.3.7.RELEASE\spring-boot-starter-web-2.3.7.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\boot\spring-boot-starter-json\2.3.7.RELEASE\spring-boot-starter-json-2.3.7.RELEASE.jar;D:\environment\Maven\Repository\com\fasterxml\jackson\core\jackson-databind\2.11.3\jackson-databind-2.11.3.jar;D:\environment\Maven\Repository\com\fasterxml\jackson\core\jackson-annotations\2.11.3\jackson-annotations-2.11.3.jar;D:\environment\Maven\Repository\com\fasterxml\jackson\core\jackson-core\2.11.3\jackson-core-2.11.3.jar;D:\environment\Maven\Repository\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.11.3\jackson-datatype-jdk8-2.11.3.jar;D:\environment\Maven\Repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.11.3\jackson-datatype-jsr310-2.11.3.jar;D:\environment\Maven\Repository\com\fasterxml\jackson\module\jackson-module-parameter-names\2.11.3\jackson-module-parameter-names-2.11.3.jar;D:\environment\Maven\Repository\org\springframework\boot\spring-boot-starter-tomcat\2.3.7.RELEASE\spring-boot-starter-tomcat-2.3.7.RELEASE.jar;D:\environment\Maven\Repository\org\apache\tomcat\embed\tomcat-embed-core\9.0.41\tomcat-embed-core-9.0.41.jar;D:\environment\Maven\Repository\org\glassfish\jakarta.el\3.0.3\jakarta.el-3.0.3.jar;D:\environment\Maven\Repository\org\apache\tomcat\embed\tomcat-embed-websocket\9.0.41\tomcat-embed-websocket-9.0.41.jar;D:\environment\Maven\Repository\org\springframework\spring-web\5.2.12.RELEASE\spring-web-5.2.12.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\spring-beans\5.2.12.RELEASE\spring-beans-5.2.12.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\spring-webmvc\5.2.12.RELEASE\spring-webmvc-5.2.12.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\spring-aop\5.2.12.RELEASE\spring-aop-5.2.12.RELEASE.jar;D:\environment\Maven\Repository\org\springframework\spring-expression\5.2.12.RELEASE\spring-expression-5.2.12.RELEASE.jar;D:\software\IntelliJ IDEA 2020.2.3\lib\idea_rt.jar" com.ji.spring5.test.beanProcessor.DigInAutowired
         * Connected to the target VM, address: '127.0.0.1:60151', transport: 'socket'
         * bean33 >>>>:com.ji.spring5.test.beanProcessor.Bean3@1d29cf23
         * bean22 >>>>:com.ji.spring5.test.beanProcessor.Bean2@167fdd33
         * bean1--->Bean1{bean2=null, bean3=null, home='null'}
         * metadata >>>> org.springframework.beans.factory.annotation.InjectionMetadata@29176cc1
         * 22:57:40.683 [main] DEBUG org.springframework.core.env.PropertySourcesPropertyResolver - Found key 'JAVA_HOME' in PropertySource 'systemEnvironment' with value of type String
         * @Value 生效：D:\environment\jdk1.8.0_241\jdk1.8.0_241
         * @Autowired 生效：com.ji.spring5.test.beanProcessor.Bean2@167fdd33
         * bean1 >>>> Bean1{bean2=com.ji.spring5.test.beanProcessor.Bean2@167fdd33, bean3=null, home='D:\environment\jdk1.8.0_241\jdk1.8.0_241'}
         * invokeBean3 >>>> com.ji.spring5.test.beanProcessor.Bean3@1d29cf23
         * invokeSetBean2 >>>> com.ji.spring5.test.beanProcessor.Bean2@167fdd33
         * 22:57:40.698 [main] DEBUG org.springframework.core.env.PropertySourcesPropertyResolver - Found key 'JAVA_HOME' in PropertySource 'systemEnvironment' with value of type String
         * invokeSetHome >>>> D:\environment\jdk1.8.0_241\jdk1.8.0_241
         * Disconnected from the target VM, address: '127.0.0.1:60151', transport: 'socket'
         *
         * Process finished with exit code 0
         */
    }
}
