package com.whitehat.springsecurity.oauth2simple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Authoer whitehat
 * @Date 2021/12/11
 **/

@MapperScan("com.whitehat.springsecurity.dao.mapper")
@ComponentScan("com.whitehat.springsecurity.service, com.whitehat.springsecurity.oauth2simple")
@SpringBootApplication
public class OAuth2SimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2SimpleApplication.class, args);
    }
}
