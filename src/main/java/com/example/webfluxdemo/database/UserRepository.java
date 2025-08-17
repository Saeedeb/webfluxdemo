package com.example.webfluxdemo.database;

import com.example.webfluxdemo.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    Mono<User> findByName(String name);
}
