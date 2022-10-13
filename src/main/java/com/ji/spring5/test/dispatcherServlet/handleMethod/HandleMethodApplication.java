package com.ji.spring5.test.dispatcherServlet.handleMethod;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;

public class HandleMethodApplication {
    public static void main(String[] args) {
        /**
         * HandleMethod 需要
         * Bean 即使那个Controller
         * Method 即是 Controller 中的那个方法
         *
         * ServletInvocableHandleMethod 需要
         *  - WebDataBinderFactory 负责对象绑定、类型转换
         *  - ParameterNameDiscoverer 负责参数名解析
         *  - HandlerMethodArgumentResolverComposite 负责参数解析
         *  - HandlerMethodReturnValueHandlerComposite 负责处理返回值
         */

        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
            RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
            adapter.setApplicationContext(context);
            adapter.afterPropertiesSet();
            Method getModelFactory = RequestMappingHandlerAdapter.class.getDeclaredMethod("getModelFactory", HandlerMethod.class, WebDataBinderFactory.class);
            getModelFactory.setAccessible(true);


            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setParameter("name","张三");
            // 现在可以通过ServletInvocableHandlerMethod 把这些整合在一起，并完成控制器方法的作用
            ServletInvocableHandlerMethod handlerMethod = new ServletInvocableHandlerMethod(new Controller1(),Controller1.class.getDeclaredMethod("get", Controller1.User.class));
            ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null,null);
            ModelAndViewContainer container = new ModelAndViewContainer(); // 模型容器

            ModelFactory modelFactory = (ModelFactory)getModelFactory.invoke(adapter, handlerMethod, factory); // 获取模型工厂对象
            modelFactory.initModel(new ServletWebRequest(request),container,handlerMethod);

            handlerMethod.setDataBinderFactory(factory);
            handlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());

            handlerMethod.setHandlerMethodArgumentResolvers(getComposite(context));


            handlerMethod.invokeAndHandle(new ServletWebRequest(request),container);
            System.out.println(container.getModel());
            context.close();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取参数解析器
     * @param context
     * @return
     */
    public static HandlerMethodArgumentResolverComposite getComposite(AnnotationConfigApplicationContext context) {
        DefaultListableBeanFactory defaultListableBeanFactory = context.getDefaultListableBeanFactory();
        HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        composite.addResolvers(
                // 解析 @RequestParam 注解的解析器， 第一个参数为beanFactory，第二个参数为是否必须有这个注解
                new RequestParamMethodArgumentResolver(defaultListableBeanFactory,false), // @RequestParam 参数解析器
                new PathVariableMethodArgumentResolver(),// @PathVariable 参数解析器
                new RequestHeaderMethodArgumentResolver(defaultListableBeanFactory), //  @RequestHeader 参数解析器
                new ServletCookieValueMethodArgumentResolver(defaultListableBeanFactory), // @CookieValue 参数解析器
                new ExpressionValueMethodArgumentResolver(defaultListableBeanFactory), // @Value 参数解析器
                new ServletRequestMethodArgumentResolver(), // 解析 HttpServletRequest
                new ServletModelAttributeMethodProcessor(false),// @ModelAttribute 参数解析器 为false为必须有@ModelAttribute注解
                new RequestResponseBodyMethodProcessor(Collections.singletonList(new FastJsonHttpMessageConverter())),// @RequestBody 参数解析器
                new ServletModelAttributeMethodProcessor(true), // @ModelAttribute 参数解析器 为true为没有@ModelAttribute注解
                new RequestParamMethodArgumentResolver(defaultListableBeanFactory,true) // @RequestParam 参数解析器
        );
        return composite;
    }
}
