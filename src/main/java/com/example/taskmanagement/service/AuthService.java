package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.LoginResponse;
import com.example.taskmanagement.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(String email, String password);

}