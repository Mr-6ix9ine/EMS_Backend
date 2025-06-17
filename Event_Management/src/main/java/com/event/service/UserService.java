package com.event.service;


import com.event.entity.User;

public interface UserService {
   public User register(User user);
    public User login(String email, String password);
}
