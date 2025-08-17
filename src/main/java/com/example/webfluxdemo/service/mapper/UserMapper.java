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
    @Mapping(target = "status", constant = "INACTIVE")
    User toUser(UserRegistrationRequest user);


}
