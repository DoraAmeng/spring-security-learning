package com.whitehat.springsecurity.oauth2simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Authoer whitehat
 * @Date 2021/12/12
 **/

@RestController
@RequestMapping("/callback")
public class TokenCallbackController {
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/code")
    public void codeCallback(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("client_id", "client1");
        map.add("client_secret", "123");
        map.add("redirect_uri", "http://localhost:8080/callback/code");
        map.add("grant_type", "authorization_code");
        Map<String,String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);

        String access_token = resp.get("access_token");
        System.out.println(access_token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        //test1 oauth认证即可访问
        //ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/resource/test1", HttpMethod.GET, httpEntity, String.class);
        //role 需要admin角色才可以访问
        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/resource/role", HttpMethod.GET, httpEntity, String.class);
        System.out.println(entity.getBody());
    }
}
