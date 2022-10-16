package com.ji.spring5.test.dispatcherServlet.mvcProcess;

public class MVCProcessApplication {
    public static void main(String[] args) {
        /**
         * mvc 处理流程
         * 当浏览器发送一个请求 http://localhost:8080/hello 后，请求达到服务器后，其处理处理流程是：
         *  1、服务器提供了 DispatcherServlet，它使用的是标准 Servlet 技术
         *      - 路径：默认映射路径为 / ，即会普配到所有请求 URL，可作为请求的统一入口，也被称为之为 前端控制器
         *          - 例外：jsp不会匹配到 DispatcherServlet
         *      - 创建：在 Boot 中 ，由 DispatcherServletAutoConfiguration 这个自动配置类提供 DispatcherServlet 的bean
         *      - 初始化：DispatcherServlet 初始化时 会优先到容器里寻找各种组件，作为他的成员变量
         *          - HandlerMapping，初始化时记录映射关系
         *          - HandlerAdapter，初始化时准备参数解析器，返回值处理器、消息转换器
         *          - HandlerExceptionResolver，异常解析器，初始化时准备参数解析器，返回值处理器、消息转换器
         *          - ViewResolver 视图解析器
         *  2、DispatcherServlet 会利用 RequestMappingHandlerMapping 进行路径匹配，找到@RequestMapping(“/hello”)对应的控制器方法
         *      - 例如根据 /hello 路径找到 @RequestMapping(“/hello”)对应的控制器方法
         *      - 控制器方法会被封装为 HandlerMethod 对象，并结合匹配到的拦截器一起返回给 DispatcherServlet
         *      - HandlerMethod 和拦截器合在一起称为 HandlerExecutionChain（调用链）对象
         *  3、DispatcherServlet 接下来会：
         *      - 调用拦截器的 preHandle 方法 返回值为 true 放行，为 false 拦截
         *      - HandlerAdapter 调用 handle 方法，准备数据绑定工厂、模型工厂。将 HandlerMethod 完善为 ServletInvocableHandlerMethod
         *          - @ControllerAdvice 增强1：补充模型数据
         *          - @ControllerAdvice 增强2：补充自定义类型转换器
         *          - 使用 HandlerMethodArgumentResolver 准备参数
         *          - @ControllerAdvice 增强3：RequestBody 增强
         *          - 调用 ServletInvocableHandlerMethod
         *          - 使用 HandlerMethodReturnValueHandler 处理返回值
         *              - 如果返回的 ModelAndView 为 null ，不走第4步视图解析及视图渲染流程
         *                  - 例如，标注了@ResponseBody的控制器方法，调用 HttpMessageConverter 来将结果转换为 JSON，这时返回的 ModelAndView 就为 null
         *              - 如果返回的 ModelAndView 不为 null，他会在第四步走视图解析及渲染流程
         *              - @ControllerAdvice 增强4：ResponseBody 增强
         *      - 调用拦截器的 postHandle 方法
         *      - 处理异常或者视图渲染
         *          - 如果1~3出现异常，走 ExceptionHandlerExceptionResolver  处理异常流程
         *              - @ControllerAdvice 增强5：@ExceptionHandler 异常处理
         *          - 正常 ，走视图解析姐渲染流程
         *      - 调用拦截器的 afterCompletion 方法
         */
    }
}
