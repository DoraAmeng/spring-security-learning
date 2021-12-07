package com.whitehat.springsecurity.dao.mapper;

import com.whitehat.springsecurity.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Authoer whitehat
 * @Date 2021/12/7
 **/

@Repository
public interface UserMapper {
    public Long addUser(User user);
    public User findUserByUsername(String username);
}
