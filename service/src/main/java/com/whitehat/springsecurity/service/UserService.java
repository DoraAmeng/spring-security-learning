package com.whitehat.springsecurity.service;

import com.whitehat.springsecurity.entity.User;

/**
 * @Authoer whitehat
 * @Date 2021/12/7
 **/

public interface UserService {
    public Long addUser(User user);
    public User findUserByUsername(String username);
}
