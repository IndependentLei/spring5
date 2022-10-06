package com.ji.spring5.test.dispatcherServlet.init;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @RequestMapping("/test1")
    public ModelAndView test1(){
        System.out.println("test1 >>>>>>>>>>");
        return null;
    }

    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name){
        System.out.println("test2 >>>>>>>>>>"+name);
        return null;
    }

    @PutMapping("/test3")
    public ModelAndView test3(@Token String token){
        System.out.println("test3 >>>>>>>>>>");
        return null;
    }

    @RequestMapping("/test4.yaml")
    @Yml
    public ModelAndView test4(){
        System.out.println("test3 >>>>>>>>>>");
        return null;
    }

}
