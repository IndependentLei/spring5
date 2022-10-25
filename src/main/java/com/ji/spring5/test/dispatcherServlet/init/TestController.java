package com.ji.spring5.test.dispatcherServlet.init;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        System.out.println("test3 >>>>>>>>>>"+token);
        return null;
    }

    @RequestMapping("/test4.yaml")
    @Yml
    public ModelAndView test4(){
        System.out.println("test4 >>>>>>>>>>");
        return null;
    }

    @RequestMapping("/test5.json")
    @ResponseBody
    public User test5(){
        System.out.println("test5 >>>>>>>>>>");
        return new User("1","1");
    }

    public static class User{
        private String name;
        private String age;

        public User (String name,String age){
            this.name = name;
            this.age = age;
        }
    }

}
