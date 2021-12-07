package com.whitehat.springsecurity.baseconfigapp.config;

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


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 403未授权访问
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin()                     //自定义登录页面
                .loginPage("/login.html")    //登录页面路径
                .usernameParameter("username").passwordParameter("password")   //设置表单传参的名称
        .loginProcessingUrl("/login")   //登录接口路径，该路径须与form表单中的action路径一致
        .defaultSuccessUrl("/index.html")  //登录成功后跳转页面
                .failureUrl("/failure.html") //登录失败后跳转页面
        .and().authorizeRequests()
                .antMatchers("/login.html", "/register.html", "/failure.html", "/user/register").permitAll()  //不需要登录的即可访问的页面
                .antMatchers("/admin.html").hasAuthority("admin")    //admin才可以访问
                //.antMatchers("/admin.html").hasAnyAuthority("admin", "manager") //admin或manager一种权限就可以访问
                .antMatchers("/role.html").hasRole("role")
        .anyRequest().authenticated()        //其他路径都需要认证
                .and().csrf().disable();     //关闭csrf防护
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
