package com.whitehat.springsecurity.baseconfigapp.controller;

import com.whitehat.springsecurity.entity.User;
import com.whitehat.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @Authoer whitehat
 * @Date 2021/11/28
 **/

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.addUser(new User(username, password));
        return "index.html";
    }

    @GetMapping("/hello")
    @Secured("ROLE_role1")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/forbidden")
    public String forbidden() {
        throw new AccessDeniedException("禁止访问测试");
    }
}
