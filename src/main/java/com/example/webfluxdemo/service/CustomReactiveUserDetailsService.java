package com.example.webfluxdemo.service;

import com.example.webfluxdemo.database.UserRepository;
import com.example.webfluxdemo.security.CustomUserDetails;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Primary
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public CustomReactiveUserDetailsService(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
//                .map(user -> new CustomUserDetails(
//                        user.getId(),
//                        user.getUsername(),
//                        user.getPassword(),
//                        Objects.equals(user.getStatus(), "ACTIVE"),
//                        userRoles.stream()
//                                .map(r -> new SimpleGrantedAuthority(r.getName()))
//                                .collect(Collectors.toList())
                .flatMap(user ->
                        userRoleService.getRolesByUserId(user.getId())
                                .map(role -> new SimpleGrantedAuthority("ROLE_" +role))
                                .collectList()
                                .map(authorities -> new CustomUserDetails(
                                        user.getId(),
                                        user.getUsername(),
                                        user.getPassword(),
                                        "ACTIVE".equals(user.getStatus()),
                                        authorities
                                ))
                );

    }
}
