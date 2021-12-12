package com.whitehat.springsecurity.oauth2simple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Authoer whitehat
 * @Date 2021/12/11
 **/

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/world")
    public String hello() {
        return "Hello World!";
    }
}
