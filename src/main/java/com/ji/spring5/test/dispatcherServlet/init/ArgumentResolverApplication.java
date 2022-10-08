package com.ji.spring5.test.dispatcherServlet.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class ArgumentResolverApplication {

    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
            DefaultListableBeanFactory defaultListableBeanFactory = context.getDefaultListableBeanFactory(); // beanFactory
            HttpServletRequest httpServletRequest = moocRequest();
            // 控制器方法封装为 HandlerMethod
            HandlerMethod handlerMethod = new HandlerMethod(new Controller(), Controller.class.getMethod("test", String.class, String.class, int.class, String.class, int.class, String.class, String.class, String.class, HttpServletRequest.class, User.class, User.class, User.class));
            // 准备对象绑定与类型转换
            ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null,null);
            // 准备 ModelAndViewContainer 用来存储中间Model结果
            ModelAndViewContainer container = new ModelAndViewContainer();
            // 解析每个参数值
            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            // 多个解析器组合
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
            for (MethodParameter methodParameter : methodParameters) {
                methodParameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer()); // 设置参数名的解析器
//                System.out.println(methodParameter + " 类型:" + methodParameter.getParameterType().getSimpleName() + " 参数名:" + methodParameter.getParameterName());
                /**
                 * method 'test' parameter 0类型:String参数名name1
                 * method 'test' parameter 1类型:String参数名name2
                 * method 'test' parameter 2类型:int参数名age
                 * method 'test' parameter 3类型:String参数名home
                 * method 'test' parameter 4类型:MultipartFile参数名file
                 * method 'test' parameter 5类型:int参数名id
                 * method 'test' parameter 6类型:String参数名header
                 * method 'test' parameter 7类型:String参数名token
                 * method 'test' parameter 8类型:String参数名home2
                 * method 'test' parameter 9类型:HttpServletRequest参数名request
                 * method 'test' parameter 10类型:User参数名user1
                 * method 'test' parameter 11类型:User参数名user2
                 * method 'test' parameter 12类型:User参数名user3
                 */
                // 是否支持此参数解析器
                if(composite.supportsParameter(methodParameter)){
                    Object v = composite.resolveArgument(methodParameter, container, new ServletWebRequest(httpServletRequest), factory);
                    System.out.println(methodParameter + " 类型:" + methodParameter.getParameterType().getSimpleName()+ " 转换之后的类型:"+v.getClass().getSimpleName() + " 参数名:" + methodParameter.getParameterName() + "----> 解析结果:"+v);
                }else {
                    System.out.println(methodParameter + " 类型:" + methodParameter.getParameterType().getSimpleName() + " 参数名:" + methodParameter.getParameterName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method 'test' parameter 0 类型:String 转换之后的类型:String 参数名:name1----> 解析结果:zhangsan
     * method 'test' parameter 1 类型:String 转换之后的类型:String 参数名:name2----> 解析结果:lisi
     * method 'test' parameter 2 类型:int 转换之后的类型:Integer 参数名:age----> 解析结果:18
     * method 'test' parameter 3 类型:String 转换之后的类型:String 参数名:home----> 解析结果:D:\environment\jdk1.8.0_241\jdk1.8.0_241
     * method 'test' parameter 4 类型:int 转换之后的类型:Integer 参数名:id----> 解析结果:123
     * method 'test' parameter 5 类型:String 转换之后的类型:String 参数名:header----> 解析结果:application/json
     * method 'test' parameter 6 类型:String 转换之后的类型:String 参数名:token----> 解析结果:123456
     * method 'test' parameter 7 类型:String 转换之后的类型:String 参数名:home2----> 解析结果:D:\environment\jdk1.8.0_241\jdk1.8.0_241
     * method 'test' parameter 8 类型:HttpServletRequest 转换之后的类型:MockHttpServletRequest 参数名:request----> 解析结果:org.springframework.mock.web.MockHttpServletRequest@433ffad1
     * method 'test' parameter 9 类型:User 转换之后的类型:User 参数名:user1----> 解析结果:ggg 18
     * method 'test' parameter 10 类型:User 转换之后的类型:User 参数名:user2----> 解析结果:ggg 18
     * method 'test' parameter 11 类型:User 转换之后的类型:User 参数名:user3----> 解析结果:xiaomi 33
     */


    /**
     * 学到了什么
     *  1、每个参数处理器能干啥
     *      - 看是否支持某种参数
     *      - 获取参数的值
     *  2、组合模式在Spring中的体现
     *  3、@RequestParam。@CookieValue等注解中的参数名、默认值，都可以写成活的，即从 ${} #{}中获取
     */


    // 模拟发送请求
    private static HttpServletRequest moocRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1", "zhangsan");
        request.setParameter("name2", "lisi");
//        request.addPart(new MockPart("file", "abc", "hello".getBytes(StandardCharsets.UTF_8)));
        Map<String, String> uriTemplateVariables = new AntPathMatcher().extractUriTemplateVariables("/test/{id}", "/test/123");
        System.out.println(uriTemplateVariables);
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriTemplateVariables);
        request.setContentType("application/json");
        request.setCookies(new Cookie("token", "123456"));
        request.setParameter("name", "ggg");
        request.setParameter("age", "18");
        String s = "{\"name\":\"xiaomi\",\"age\":33}";
        request.setContent(s.getBytes(StandardCharsets.UTF_8));
        return request;
    }

    static class Controller {

        @RequestMapping("/test/{id}")
        public void test(@RequestParam("name1") String name1, String name2, // name2 = "222" ,和上面一样
                         @RequestParam("age") int age, // age = 1
                         @RequestParam(name = "home", defaultValue = "${JAVA_HOME}") String home, // Spring中获取
//                         @RequestParam("file") MultipartFile file, // 文件
                         @PathVariable("id") int id, // 请求路径中获取
                         @RequestHeader("Content-Type") String header, // 请求头上获取
                         @CookieValue("token") String token, // Cookie中获取
                         @Value("${JAVA_HOME}") String home2, // Spring 获取数据
                         HttpServletRequest request, // request
                         @ModelAttribute User user1,  // name=zhangsan&age=1
                         User user2,  // name=zhangsan&age=1  就是省略了 @ModelAttribute这个注解
                         @RequestBody User user3) {

            // TODO....
            System.out.println("666666666");
        }
    }

    static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString(){
            return this.name+" "+this.age;
        }
    }
}
