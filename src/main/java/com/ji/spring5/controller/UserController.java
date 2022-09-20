package com.ji.spring5.controller;

import com.ji.spring5.entity.UserRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @ClassName : UserController
 * @Author : jdl
 * @Create : 2022-09-13 22:02
 */
@RestController
public class UserController {

    @EventListener
    public void resolve(UserRegisteredEvent event){
        System.out.println(event.getSource());
    }
}
