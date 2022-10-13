package com.ji.spring5.test.dispatcherServlet.handleMethod;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controller1 {

    @ResponseStatus(HttpStatus.OK)
    public ModelAndView get(User user){
        System.out.println(user.toString());
        return null;
    }

    @ControllerAdvice
    static class MyControllerAdvice{
        @ModelAttribute("a")
        public String aa(){
            System.out.println("------------> ModelAttribute");
            return "aaa";
        }
    }

    static class User{
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
