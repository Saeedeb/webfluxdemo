package com.example.webfluxdemo.service;

import com.example.webfluxdemo.controller.dtos.UserRegistrationRequest;
import com.example.webfluxdemo.controller.dtos.UserRegistrationResponse;
import com.example.webfluxdemo.database.UserRepository;
import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.security.CustomUserDetails;
import com.example.webfluxdemo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final  PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository repository, UserMapper userMapper, PasswordEncoder passwordEncoder)  {
        this.repository = repository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

    }

    public Mono<User> findById(Long id) {
        return repository.findById(id);
    }
    public Mono<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
    public Flux<User> findAll() {
        return repository.findAll();
    }
    @Transactional
    public Mono<UserRegistrationResponse> save(Mono<UserRegistrationRequest> request) {

//        return ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .flatMap(auth ->{
//                    Integer userId  =((CustomUserDetails) auth).getId();
                    return request
                            .map(userMapper::toUser)
                            .map(user -> {
                                user.setPassword(passwordEncoder.encode(user.getPassword()));
                                return user;
                            })
                            .flatMap(repository::save)
                            .map( u-> new UserRegistrationResponse(u.getEmail()))
                            .doOnNext(x ->log.info("Saving user {} ",x.email ));
               // });


    }
    public Mono<UserRegistrationResponse> update(Long id, Mono<User> user) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id " + id)))
                .flatMap(existingUser -> user.map(newUser -> {
                     existingUser.setName(newUser.getName());
                     existingUser.setEmail(newUser.getEmail());
                     return existingUser;
                 }))
                .map( u-> new UserRegistrationResponse(u.getEmail())).
                doOnNext(x ->log.info("Saving user {}",x.email ));


    }
    public Mono<User> partialUpdate(Long id, Map<String, Object> updates) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(existingUser -> {
                    if (updates.containsKey("name")) {
                        Object newName = updates.get("name");
                        if (newName instanceof String) {
                            existingUser.setName((String) newName);
                        }
                    }if (updates.containsKey("email")) {
                        Object newName = updates.get("email");
                        if (newName instanceof String) {
                            existingUser.setEmail((String) newName);
                        }
                    }
                    return repository.save(existingUser);
                });
    }
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}