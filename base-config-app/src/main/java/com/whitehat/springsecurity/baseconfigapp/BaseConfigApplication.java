package com.whitehat.springsecurity.baseconfigapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Authoer whitehat
 * @Date 2021/12/7
 **/

@MapperScan("com.whitehat.springsecurity.dao.mapper")
@ComponentScan("com.whitehat.springsecurity.service, com.whitehat.springsecurity.baseconfigapp")
@SpringBootApplication
public class BaseConfigApplication {
    public static void main(String[] args){
        SpringApplication.run(BaseConfigApplication.class, args);
    }
}
