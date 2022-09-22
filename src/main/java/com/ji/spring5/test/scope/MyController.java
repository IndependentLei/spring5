package com.ji.spring5.test.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class MyController {

    @Lazy
    @Autowired
    private BeanForRequest beanForRequest;

    @Lazy
    @Autowired
    private BeanForApplication beanForApplication;

    @Lazy
    @Autowired
    private BeanForSession beanForSession;


    @GetMapping(value = "test",produces = "text/html")
    public String test(HttpServletRequest request, HttpSession httpSession){
        ServletContext sc = request.getServletContext();
        String sb = "<ul>"+
                "<li>"+"request scope:"+beanForRequest +"</li>"+
                "<li>"+"application scope:"+beanForApplication +"</li>"+
                "<li>"+"session scope:"+beanForSession +"</li>"+
                "<ul>";
        return sb;
    }

    /**
     * request scope:com.ji.spring5.test.scope.BeanForRequest@6d4e908e   // 每次请求
     * application scope:com.ji.spring5.test.scope.BeanForApplication@6fef3326   // 应用程序
     * session scope:com.ji.spring5.test.scope.BeanForSession@5529402a  // 会话级别
     */

    /**
     * request scope:com.ji.spring5.test.scope.BeanForRequest@54ff4375
     * application scope:com.ji.spring5.test.scope.BeanForApplication@6fef3326
     * session scope:com.ji.spring5.test.scope.BeanForSession@698be676
     */


}
