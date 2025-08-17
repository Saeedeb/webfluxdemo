package com.example.webfluxdemo.controller;


import com.example.webfluxdemo.controller.dtos.UserRegistrationRequest;
import com.example.webfluxdemo.controller.dtos.UserRegistrationResponse;
import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.service.UserService;
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

    @GetMapping
    public Flux<User> getAllUsers() throws InterruptedException {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping ()
    public Mono<UserRegistrationResponse> post(@Valid  @RequestBody Mono<UserRegistrationRequest> request) {
        return userService.save(request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser( @PathVariable Integer id) {
        return userService.deleteById(id);
    }
    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable Integer id, @Validated @RequestBody Mono<User> user) {
        return userService.update(id,user);
    }
    @PatchMapping("/{id}")
    public Mono<User> partialUpdateUser(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        return  userService.partialUpdate(id, updates);
    }
}
