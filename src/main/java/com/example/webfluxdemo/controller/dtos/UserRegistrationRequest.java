package com.example.webfluxdemo.controller.dtos;

import jakarta.validation.constraints.NotNull;

public class UserRegistrationRequest {

    @NotNull
    public String fullName;
    public String email;
    @NotNull
    public String password;
    public String confirmPassword;


}