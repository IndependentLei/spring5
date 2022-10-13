package com.ji.spring5.test.dispatcherServlet.controllerAdvice;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ControllerAdviceApplication {
    public static void main(String[] args) {
        /**
         * @InitBinder
         * 1、@ControllerAdvice 中 @InitBinder 标注的方法，由 RequestMappingHandlerAdapter 在初始化时候解析并记录
         * 2、@Controller 中 @InitBinder 标注的方法 由 RequestMappingHandlerAdapter 会在控制器首次执行时候解析并记录
         */

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setApplicationContext(context);
        requestMappingHandlerAdapter.afterPropertiesSet();
        System.out.println("1.刚开始");
        showInitBinderCache(requestMappingHandlerAdapter);
        context.close();
    }

    public static void showInitBinderCache(RequestMappingHandlerAdapter requestMappingHandlerAdapter){
        try {
            // 用到的时候才初始化
            Method getDataBinderFactory = RequestMappingHandlerAdapter.class.getDeclaredMethod("getDataBinderFactory", HandlerMethod.class);
            getDataBinderFactory.setAccessible(true);
            Method test = WebConfig.MyController.class.getDeclaredMethod("test");
            getDataBinderFactory.invoke(requestMappingHandlerAdapter,new HandlerMethod(new WebConfig.MyController(),test));
            Field initBinderCache = RequestMappingHandlerAdapter.class.getDeclaredField("initBinderCache");
            initBinderCache.setAccessible(true);
            Map<Class<?>, Set<Method>> initBinderCacheMap = (Map<Class<?>, Set<Method>>)initBinderCache.get(requestMappingHandlerAdapter);
            System.out.println("initBinderCache ---------------------------");
            initBinderCacheMap.forEach((k,v)->{
                System.out.println("k -> "+k.getSimpleName() +" : "+ "v -> "+ Arrays.toString(v.toArray()));
            });

            // 刚开始就初始化
            System.out.println("initBinderAdviceCache ---------------------------");
            Field initBinderAdviceCache = RequestMappingHandlerAdapter.class.getDeclaredField("initBinderAdviceCache");
            initBinderAdviceCache.setAccessible(true);
            Map<ControllerAdviceBean, Set<Method>> initBinderAdviceCacheMap = (Map< ControllerAdviceBean, Set<Method>>)initBinderAdviceCache.get(requestMappingHandlerAdapter);
            initBinderAdviceCacheMap.forEach((k,v)->{
                System.out.println("k -> "+k.getBeanType().getSimpleName() +" : "+ "v -> "+ Arrays.toString(v.toArray()));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /**
         * 学到了
         *  1、Method 对象内获取利用了缓存来进行加速
         *  2、绑定器工厂的扩展点（advice 之一）通过 @InitBinder 扩展类型转换器
         */
    }
}
