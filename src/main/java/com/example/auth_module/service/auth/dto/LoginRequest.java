package com.example.auth_module.service.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
