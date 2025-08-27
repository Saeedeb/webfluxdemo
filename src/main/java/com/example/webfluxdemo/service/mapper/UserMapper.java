package com.example.webfluxdemo.service.mapper;

import com.example.webfluxdemo.controller.dtos.UserRegistrationRequest;
import com.example.webfluxdemo.model.User;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    //@Mapping(target = "refId", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "name", source = "fullName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", constant = "INACTIVE")
    @Mapping(target = "roles",ignore = true)// constant = "user")
    @Mapping(target = "username",
            expression = "java(user.username != null && !user.username.isEmpty() ? user.username : user.email)")
    User toUser(UserRegistrationRequest user);


}
