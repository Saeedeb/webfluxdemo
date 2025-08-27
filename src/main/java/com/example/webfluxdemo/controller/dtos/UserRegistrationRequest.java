package com.example.webfluxdemo.controller.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "create user model")
public class UserRegistrationRequest {

    @Schema(description = "fullName is required",example = "AliRest123456")
    @NotNull
    public String fullName;
    @Schema(description = "fullName is not required ",example = "ali@test.com")
    public String email;

    @Schema(description = "fullName is not required ",example = "ali123")
    public String username;
    @Schema(description = "password  is required" ,example = "Aa@1234567")
    @NotNull
    public String password;
    @Schema(description = "confirmPassword  is required ")
    public String confirmPassword;


}