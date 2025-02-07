package com.example.cloudservice.service;

import com.example.cloudservice.entity.User;

public interface UserService {
    User getByLogin(String username);
}
