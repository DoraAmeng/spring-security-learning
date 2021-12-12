package com.whitehat.springsecurity.oauth2simple.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Authoer whitehat
 * @Date 2021/12/11
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    @Bean
//    RemoteTokenServices tokenServices() {
//        RemoteTokenServices services = new RemoteTokenServices();
//        services.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//        services.setClientId("javaboy");
//        services.setClientSecret("123");
//        return services;
//    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // super.configure(resources);
        resources.resourceId("resource1");
        resources.accessDeniedHandler((request, response, accessDeniedException) -> {
            response.sendRedirect("/oauth2accessdeny.html");
        });
        resources.authenticationEntryPoint((request, response, accessDeniedException) -> {
            response.sendRedirect("/oauth2login.html");
        });
        //resources.resourceId("res1").tokenServices(tokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers().antMatchers("/resource/**");

//        http.authorizeRequests()
//                .antMatchers("/resource/**").hasRole("admin")
//                .anyRequest().authenticated();

        http.requestMatchers().antMatchers("/resource/**")
                .and().authorizeRequests()
                .antMatchers(("/resource/noauth")).permitAll() //普通登录即可访问，不需要oauth登录
                .antMatchers("/resource/role").hasRole("admin")
                .anyRequest().authenticated();
    }
}
