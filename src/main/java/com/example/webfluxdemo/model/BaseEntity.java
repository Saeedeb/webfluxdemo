package com.example.webfluxdemo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity<TKeyType>  {

    // getter و setter
    @Id
     TKeyType id;

    @CreatedBy
    Long createdBy;

    @CreatedDate
     LocalDateTime createdAt;

    @LastModifiedBy
    Long updatedBy;

    @LastModifiedDate()
     LocalDateTime updatedAt;

}