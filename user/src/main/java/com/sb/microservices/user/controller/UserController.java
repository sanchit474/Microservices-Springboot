package com.sb.microservices.user.controller;

import java.util.List;

import com.sb.microservices.user.entity.User;
import com.sb.microservices.user.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServices userServices;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User response = userServices.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByID(@PathVariable String userId){
        User user = userServices.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUsers =userServices.getAllUser();
        return ResponseEntity.ok(allUsers);
    }
}
