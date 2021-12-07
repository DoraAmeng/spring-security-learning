package com.whitehat.springsecurity.separationapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

/**
 * @Authoer whitehat
 * @Date 2021/11/28
 **/

@Configuration
//开启注解授权
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password = passwordEncoder.encode("123");
//        auth.inMemoryAuthentication().withUser("admin").password(password).roles("admin");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //前后端分离采用如下配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")   //设置表单传参的名称
                //https://blog.csdn.net/qq_42033495/article/details/106617448(successHandler, failureUrl)
                .successHandler((req, res, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    System.out.println(principal);
                    res.setContentType("application/json;charset=utf8");
                    PrintWriter writer = res.getWriter();
                    writer.write("{\"message\": \"登录成功\"}");
                    writer.flush();
                    writer.close();
                })
                .failureHandler((req, res, exception) -> {
                    res.setContentType("application/json;charset=utf8");
                    PrintWriter writer = res.getWriter();
                    System.out.println(exception.getMessage());
                    writer.write("{\"message\": \"登录失败。failureHandler\"}");
                    writer.flush();
                    writer.close();
                })
                .and().exceptionHandling()
                .authenticationEntryPoint((req, res, exception) -> {
                    res.setContentType("application/json;charset=utf8");
                    PrintWriter writer = res.getWriter();
                    System.out.println(exception.getMessage());
                    writer.write("{\"message\": \"检测到未登录状态，请先登录。authenticationEntryPoint\"}");
                    writer.flush();
                    writer.close();
                })
                .accessDeniedHandler((req, res, exception) -> {
                    res.setContentType("application/json;charset=utf8");
                    PrintWriter writer = res.getWriter();
                    System.out.println(exception.getMessage());
                    writer.write("{\"message\": \"accessDeniedHandler\"}");
                    writer.flush();
                    writer.close();
                })
                .and().authorizeRequests()
                .antMatchers("/login.html", "/register.html", "/failure.html", "/user/register").permitAll()  //不需要登录的即可访问的页面
                .antMatchers("/admin.html").hasAuthority("admin")    //admin才可以访问
                //.antMatchers("/admin.html").hasAnyAuthority("admin", "manager") //admin或manager一种权限就可以访问
                .antMatchers("/role.html").hasRole("role")
                .anyRequest().authenticated()        //其他路径都需要认证
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
