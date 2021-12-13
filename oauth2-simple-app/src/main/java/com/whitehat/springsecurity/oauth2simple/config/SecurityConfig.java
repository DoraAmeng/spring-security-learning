package com.whitehat.springsecurity.oauth2simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Authoer whitehat
 * @Date 2021/12/9
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("baojiasheng")
                .password(new BCryptPasswordEncoder().encode("666"))
                .roles("admin")
                .and()
                .withUser("javaboy")
                .password(new BCryptPasswordEncoder().encode("123"))
                .roles("user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 403未授权访问
        http.exceptionHandling().accessDeniedPage("/403.html");

        http.csrf().disable()
                .formLogin()
                .loginPage("/login.html")
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/login")   //登录接口路径，该路径须与form表单中的action路径一致
                .defaultSuccessUrl("/index.html")  //登录成功后跳转页面
                .failureUrl("/error.html") //登录失败后跳转页面
                .and()
                .exceptionHandling().accessDeniedPage("/403.html")
                .and().authorizeRequests()
                .antMatchers("/login.html", "/register.html", "/error.html", "/user/register",
                        "/oauth2accessdeny.html", "/oauth2login.html", "/oauth2unlogin.html").permitAll()
                //.antMatchers("/oauth/token").permitAll()   //这个地方必须配置，否则无法oauth token
               // .antMatchers("/oauth/**").permitAll()
                .antMatchers("/admin.html").hasRole("admin")    //admin才可以访问
                //.antMatchers("/admin.html").hasAnyAuthority("admin", "manager") //admin或manager一种权限就可以访问
                .antMatchers("/authority.html").hasAuthority("authority")
                .anyRequest().authenticated();        //其他路径都需要认证
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
