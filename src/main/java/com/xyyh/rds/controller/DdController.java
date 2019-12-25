package com.xyyh.rds.controller;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyyh.rds.entity.User;
import com.xyyh.rds.repository.UserRepository;

@RequestMapping("users")
@RestController
public class DdController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Optional<User> get() {
        return userRepository.findById(1L);
    }

    @PostMapping
    @Transactional
    public User write() {
        User user = new User();
        user.setName("name" + ZonedDateTime.now().toString());
        userRepository.save(user);
        return user;
    }

}
