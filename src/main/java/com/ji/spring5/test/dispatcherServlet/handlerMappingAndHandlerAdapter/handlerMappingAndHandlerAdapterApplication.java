package com.ji.spring5.test.dispatcherServlet.handlerMappingAndHandlerAdapter;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

public class handlerMappingAndHandlerAdapterApplication {
    public static void main(String[] args) {
        /**
         * 自定义 HandlerMapping 和 HandlerAdapter
         */
        AnnotationConfigServletWebServerApplicationContext applicationContext = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

        /**
         * 1、HandleMapping 负责建立请求与控制器之间的映射关系
         *      - RequestMappingHandlerMapping （与 @RequestMapping 匹配）
         *      - WelcomePageHandlerMapping （/）
         *      - BeanNameUrlHandlerMapping （与 bean 的名字匹配 以 / 开头）
         *      - RouterFunctionMapping （函数式 RequestPredicate，HandlerFunction）
         *      - SimpleUrlHandlerMapping （静态资源 通配符 /** /img/** ）
         *  2、HandlerAdapter 负责实现对各种的 Handler 的适配调用
         *      - RequestMappingHandlerAdapter 处理 @RequestMapping 方法 参数解析器、返回值处理器 体现了 组合模式
         *      - SimpleControllerHandlerAdapter 处理，Controller 接口
         *      - HandlerFunctionAdapter 处理，HandlerFunction 函数式接口
         *      - HttpRequestHandlerAdapter 处理，HttpRequestHandler 接口
         *  3、 ResourceHttpRequestHandler.setResourceResolvers(资源解析器集合) 这是检点的责任链模式的体现
         */
    }
}
