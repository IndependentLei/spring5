package com.ji.spring5.test.dispatcherServlet.init;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
//            MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test1");
            MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test5.json");
//            request.addParameter("name","zhangSan");
//            request.addHeader("token","token");
            MockHttpServletResponse response = new MockHttpServletResponse();
            HandlerExecutionChain chain = handlerMapping.getHandler(request);
            System.out.println(chain);
            // HandlerExecutionChain with [com.ji.spring5.test.dispatcherServlet.init.TestController#test1()] and 0 interceptors

            if(!Objects.isNull(chain)) {
                // 调用方法的处理器
                System.out.println(">>>>>>>>>>>>>>>>>> 调用方法的处理器");
                MyRequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(MyRequestMappingHandlerAdapter.class);
                handlerAdapter.invokeHandlerMethod(request, response, (HandlerMethod) chain.getHandler());

                String contentAsString = response.getContentAsString();
                System.out.println("contentAsString >>>>>>>>>>>>> "+contentAsString);
                // test1 >>>>>>>>>>

                System.out.println(">>>>>>>>>>>>>>>>>> 参数解析器");
                // 参数解析器
                List<HandlerMethodArgumentResolver> argumentResolvers = handlerAdapter.getArgumentResolvers();
                for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
                    System.out.println(argumentResolver);
                    /**
                     * org.springframework.web.method.annotation.RequestParamMethodArgumentResolver@5ac7aa18
                     * org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver@4cdd2c73
                     * org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver@4abf3f0
                     * org.springframework.web.servlet.mvc.method.annotation.PathVariableMapMethodArgumentResolver@4e4c3a38
                     * org.springframework.web.servlet.mvc.method.annotation.MatrixVariableMethodArgumentResolver@293cde83
                     * org.springframework.web.servlet.mvc.method.annotation.MatrixVariableMapMethodArgumentResolver@c27d163
                     * org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor@57c88764
                     * org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor@78faea5f
                     * org.springframework.web.servlet.mvc.method.annotation.RequestPartMethodArgumentResolver@37fdfb05
                     * org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver@5e39850
                     * org.springframework.web.method.annotation.RequestHeaderMapMethodArgumentResolver@1603dc2f
                     * org.springframework.web.servlet.mvc.method.annotation.ServletCookieValueMethodArgumentResolver@398474a2
                     * org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver@61799544
                     * org.springframework.web.servlet.mvc.method.annotation.SessionAttributeMethodArgumentResolver@78c1a023
                     * org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver@70abf9b0
                     * org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver@6a10b263
                     * org.springframework.web.servlet.mvc.method.annotation.ServletResponseMethodArgumentResolver@476ec9d0
                     * org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor@325bb9a6
                     * org.springframework.web.servlet.mvc.method.annotation.RedirectAttributesMethodArgumentResolver@1d12b024
                     * org.springframework.web.method.annotation.ModelMethodProcessor@72fe8a4f
                     * org.springframework.web.method.annotation.MapMethodProcessor@43effd89
                     * org.springframework.web.method.annotation.ErrorsMethodArgumentResolver@2c16fadb
                     * org.springframework.web.method.annotation.SessionStatusMethodArgumentResolver@248deced
                     * org.springframework.web.servlet.mvc.method.annotation.UriComponentsBuilderMethodArgumentResolver@2227a6c1
                     * org.springframework.web.method.annotation.RequestParamMethodArgumentResolver@1e9804b9
                     * org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor@543e593
                     */
                }

                System.out.println(">>>>>>>>>>>>>>>>>> 返回值解析器");
                List<HandlerMethodReturnValueHandler> returnValueHandlers = handlerAdapter.getReturnValueHandlers();
                for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
                    System.out.println(returnValueHandler);
                    /**
                     * org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler@1e9804b9
                     * org.springframework.web.method.annotation.ModelMethodProcessor@543e593
                     * org.springframework.web.servlet.mvc.method.annotation.ViewMethodReturnValueHandler@4e628b52
                     * org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler@51ec2df1
                     * org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBodyReturnValueHandler@f8f56b9
                     * org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor@15fa55a6
                     * org.springframework.web.servlet.mvc.method.annotation.HttpHeadersReturnValueHandler@4f186450
                     * org.springframework.web.servlet.mvc.method.annotation.CallableMethodReturnValueHandler@7fab4be7
                     * org.springframework.web.servlet.mvc.method.annotation.DeferredResultMethodReturnValueHandler@a64e035
                     * org.springframework.web.servlet.mvc.method.annotation.AsyncTaskMethodReturnValueHandler@4d74c3ba
                     * org.springframework.web.method.annotation.ModelAttributeMethodProcessor@41c204a0
                     * org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor@64138b0c
                     * org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler@49dbaaf3
                     * org.springframework.web.method.annotation.MapMethodProcessor@22d9c961
                     * org.springframework.web.method.annotation.ModelAttributeMethodProcessor@736f3e9e
                     */
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
