package com.ji.spring5.test.dispatcherServlet.handlerMappingAndHandlerAdapter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@ComponentScan
public class WebConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }

    // 创建DispatcherServlet
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }

    // 注册 DispatcherServlet ，Spring MVC 的入口
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet){
        DispatcherServletRegistrationBean dispatcherServletRegistrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        dispatcherServletRegistrationBean.setLoadOnStartup(1); // 程序启动就初始化 DispatcherServlet
        return dispatcherServletRegistrationBean;
    }


    @Component("/c1")
    static class Controller1 implements Controller{

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().print("<h3> this is c1 </h3>");
            return null;
        }
    }

    @Component
    static class MyHandlerMapping implements HandlerMapping, InitializingBean {

        @Autowired
        ApplicationContext context;

        private Map<String,Controller> map;

        @Override
        public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
            String requestURI = request.getRequestURI();
            Controller controller = map.get(requestURI); // 处理映射的map
            if(Objects.isNull(controller)){
                return null;
            }
            return new HandlerExecutionChain(controller);
        }

        @Override
        public void afterPropertiesSet() throws Exception { // 初始化调用
            // 初始化获取 所有的 控制器
            Map<String, Controller> beansMap = context.getBeansOfType(Controller.class);
            map = beansMap.entrySet().stream().filter(item -> item.getKey().contains("/")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            System.out.println("map -------------> "+map);
        }
    }

    @Component
    static class MyHandlerAdapter implements HandlerAdapter{

        @Override
        public boolean supports(Object handler) {
            return handler instanceof Controller;  // 是不是实现了Controller类型
        }

        @Override
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Controller controller = (Controller)handler;
            controller.handleRequest(request,response); // 调用 Controller 接口中的方法
            return null;
        }

        @Override
        public long getLastModified(HttpServletRequest request, Object handler) {
            return -1;
        }
    }

    @Component("c2")
    static class Controller2 implements Controller{

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().print("<h3> this is c2 </h3>");
            return null;
        }
    }

    @Component("/c3")
    static class Controller3 implements Controller{

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().print("<h3> this is c3 </h3>");
            return null;
        }
    }
}
