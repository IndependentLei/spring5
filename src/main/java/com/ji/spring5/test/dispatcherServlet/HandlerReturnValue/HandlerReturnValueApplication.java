package com.ji.spring5.test.dispatcherServlet.HandlerReturnValue;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ji.spring5.test.dispatcherServlet.init.TestController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class HandlerReturnValueApplication {
    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
            Method get = Controller.class.getDeclaredMethod("get");
            Controller controller = new Controller();
            Object returnValue = get.invoke(controller);

            HandlerMethod handlerMethod = new HandlerMethod(controller, get);

            HandlerMethodReturnValueHandlerComposite composite = getComposite();

            ModelAndViewContainer container = new ModelAndViewContainer();
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
            if(composite.supportsReturnType(handlerMethod.getReturnType())){ // 检查是否支持此类型的返回值处理器

                composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,servletWebRequest);
            }
            System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8)); // {"name":"1"}

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    
    public static HandlerMethodReturnValueHandlerComposite getComposite(){
        HandlerMethodReturnValueHandlerComposite composite = new HandlerMethodReturnValueHandlerComposite();
        composite.addHandler(new RequestResponseBodyMethodProcessor(Collections.singletonList(new MappingJackson2HttpMessageConverter())));
        return composite;
    }

    static class R{
        private String code;
        private Object data;

        public R(String code,Object data){
            this.code = code;
            this.data = data;
        }

        public static R success (Object data){
            return new R("200",data);
        }
    }

    @ControllerAdvice
    static class MyControllerAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            if(body instanceof R){
                return body;
            }
            return R.success(body);
        }
    }


    @RestController
    static class Controller {

        @ResponseBody
        public User get(){
            System.out.println("get >>>>>>>>>>");
            return new User("1");
        }



    }

    static class User{
        private String name;

        public User(String name){
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
