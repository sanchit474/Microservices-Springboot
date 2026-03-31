package com.sb.microservices.user.services;

import com.sb.microservices.user.entity.User;

import java.util.List;

public interface UserServices {
    //create user
    User saveUser(User user);
    //get all users
    List<User> getAllUser();
    //get user by id
    User getUserById(String userId);
}
