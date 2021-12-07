package com.whitehat.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Authoer whitehat
 * @Date 2021/11/28
 **/

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("admin, employer, ROLE_role1");
        com.whitehat.springsecurity.entity.User user = userService.findUserByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
