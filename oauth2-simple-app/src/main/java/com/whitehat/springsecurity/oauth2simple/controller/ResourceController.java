package com.whitehat.springsecurity.oauth2simple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Authoer whitehat
 * @Date 2021/12/11
 **/

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/test1")
    public String test1() {
        return "resource server test1";
    }

    @GetMapping("/noauth")
    public String noAuth() { return "no auth test..." ; }

    @GetMapping("/role")
    public String roleTest() {
        return "oauth2 role角色可以访问。。。";
    }
}
