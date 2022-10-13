package com.ji.spring5.test.dispatcherServlet.controllerAdvice;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.AbstractNumberFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.NumberFormat;
import java.util.Locale;

@Configuration
public class WebConfig {
    @ControllerAdvice
    static class MyControllerAdvice{
        @InitBinder // 添加一些自定义转换器
        public void bind1(WebDataBinder webDataBinder){
            webDataBinder.addCustomFormatter(new DateFormatter("yyyy|MM|dd"));
        }

        @ExceptionHandler // 处理异常
        public void runtimeExceptionHandler(RuntimeException e){
            System.out.println(e.getMessage());
        }

        @ModelAttribute // 补充模型数据
        public void modelAttribute(){

        }
    }

    @Controller
    static class MyController{
        @InitBinder
        public void bind2(WebDataBinder webDataBinder){
            webDataBinder.addCustomFormatter(new DateFormatter("yyyy|MM|dd"));
        }

        public void test(){
            System.out.println("-----------");
        }
    }
}
