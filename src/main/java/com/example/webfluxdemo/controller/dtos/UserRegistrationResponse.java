package com.example.webfluxdemo.controller.dtos;

import jakarta.validation.constraints.NotNull;

public class UserRegistrationResponse {
    public UserRegistrationResponse(String email) {
        this.email = email;
    }

    public String email;

}