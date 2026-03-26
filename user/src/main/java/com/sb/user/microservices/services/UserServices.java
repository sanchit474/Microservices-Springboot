package com.sb.user.microservices.services;

import com.sb.user.microservices.entity.User;

import java.util.List;

public interface UserServices {
    //create user
    User saveUser(User user);
    //get all users
    List<User> getAllUser();
    //get user by id
    User getUserById(String userId);
}
