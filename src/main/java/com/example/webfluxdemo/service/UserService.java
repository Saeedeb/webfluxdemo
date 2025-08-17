package com.example.webfluxdemo.service;

import com.example.webfluxdemo.controller.dtos.UserRegistrationRequest;
import com.example.webfluxdemo.controller.dtos.UserRegistrationResponse;
import com.example.webfluxdemo.database.UserRepository;
import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserRepository repository, UserMapper userMapper)  {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public Mono<User> findById(Integer id) {
        return repository.findById(id);
    }
    public Flux<User> findAll() {
        return repository.findAll();
    }
    public Mono<UserRegistrationResponse> save(Mono<UserRegistrationRequest> request) {
        return request.
                map(userMapper::toUser).
                flatMap(repository::save).
                map( u-> new UserRegistrationResponse(u.getEmail())).
                doOnNext((x -> {
                    log.info("Saving user {}",x.email );
                }));
    }
    public Mono<User> update(Integer id, Mono<User> user) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id " + id)))
                .flatMap(existingUser -> user.map(newUser -> {
                     existingUser.setName(newUser.getName());
                     existingUser.setEmail(newUser.getEmail());
                     return existingUser;
                 })).flatMap(repository::save);


    }
    public Mono<User> partialUpdate(Integer id, Map<String, Object> updates) {
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
    public Mono<Void> deleteById(Integer id) {
        return repository.deleteById(id);
    }
}