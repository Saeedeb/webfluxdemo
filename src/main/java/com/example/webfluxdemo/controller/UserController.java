package com.example.webfluxdemo.controller;


import com.example.webfluxdemo.controller.dtos.UserRegistrationRequest;
import com.example.webfluxdemo.controller.dtos.UserRegistrationResponse;
import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;
import jakarta.validation.Valid;
import java.util.Map;
@ControllerAdvice
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "List all users", description = "Returns all users")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @GetMapping
    public Flux<User> getAllUsers() throws InterruptedException {
        return userService.findAll();
    }

    @Operation(summary = "Get user by ID", description = "Returns a single user")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Create user", description = "Returns  email")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @PostMapping ()
    public Mono<UserRegistrationResponse> post(@Valid  @RequestBody Mono<UserRegistrationRequest> request) {
        return userService.save(request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser( @PathVariable Long id) {
        return userService.deleteById(id);
    }
    @PutMapping("/{id}")
    public Mono<UserRegistrationResponse> updateUser(@PathVariable Long id, @Validated @RequestBody Mono<User> user) {
        return userService.update(id,user);
    }
    @PatchMapping("/{id}")
    public Mono<User> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return  userService.partialUpdate(id, updates);
    }
}
