package com.example.webfluxdemo.service;

import com.example.webfluxdemo.controller.dtos.UserRegistrationRequest;
import com.example.webfluxdemo.database.UserRepository;
import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService service;



    @Test
    void saveUser_success() {
        //Runnable
       // Callable
        UserRegistrationRequest dto = new UserRegistrationRequest();
        dto.fullName = "Ali";
        dto.email = "ali@example.com";
        dto.password = "pass";
        dto.confirmPassword = "pass";

        User saved = new User(1L, dto.email,dto.fullName, dto.email, dto.password,"INACTIVE");

        when(userMapper.toUser(any(UserRegistrationRequest.class))).thenReturn(saved);
        when(repository.save(any(User.class))).thenReturn(Mono.just(saved));


        StepVerifier.create(service.save(Mono.just(dto)))
                .expectNextMatches(u -> u.email.equals("ali@example.com"))
                .verifyComplete();
    }
    @Test
    void findAllUsers_success() {

        User user1 = new User(1L,"ali@example.com", "ali", "ali@example.com", "pass","ACTIVE");
        User user2 = new User(2L,"ali@example.com", "sara", "sara@example.com", "pass","ACTIVE");



        when(repository.findAll()).thenReturn(Flux.just(user1,user2));


        StepVerifier.create(service.findAll())
                .expectNextMatches(u -> u.getName().equals("Ali"))
                .expectNextMatches(u -> u.getName().equals("Sara"))
                .verifyComplete();
    }


}
