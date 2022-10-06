package com.ji.spring5.test.dispatcherServlet.init;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

public class InitApplication {
    public static void main(String[] args) {
        try {
            AnnotationConfigServletWebServerApplicationContext applicationContext = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
            /**
             *     protected void initStrategies(ApplicationContext context) {
             *         this.initMultipartResolver(context);  // 处理文件
             *         this.initLocaleResolver(context);     // 处理国际化
             *         this.initThemeResolver(context);
             *         this.initHandlerMappings(context);    // 处理路径映射
             *         this.initHandlerAdapters(context);    // 适配器
             *         this.initHandlerExceptionResolvers(context);  // 异常处理
             *         this.initRequestToViewNameTranslator(context);
             *         this.initViewResolvers(context);
             *         this.initFlashMapManager(context);
             *     }
             */

            // DispatcherServlet 初始化

            // 解析 @RequestMapping 的注解以及派生注解
            RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
            handlerMethods.forEach((k,v)-> System.out.println(k+"="+v));

            // 请求来了，获取控制器方法，返回处理器执行链对象
            HandlerExecutionChain chain = handlerMapping.getHandler(new MockHttpServletRequest("GET", "/test1"));
            System.out.println(chain);
            // HandlerExecutionChain with [com.ji.spring5.test.dispatcherServlet.init.TestController#test1()] and 0 interceptors
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
