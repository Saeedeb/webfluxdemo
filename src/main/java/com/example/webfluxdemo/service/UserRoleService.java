package com.example.webfluxdemo.service;

import com.example.webfluxdemo.database.RoleRepository;
import com.example.webfluxdemo.database.UserRoleRepository;
import com.example.webfluxdemo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public Flux<String> getRolesByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                .map(Role::getName);
    }
}