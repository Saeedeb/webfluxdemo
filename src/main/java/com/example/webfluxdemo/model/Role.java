package com.example.webfluxdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Table("roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity<Long> {
    private String name; // مثلا ADMIN, USER, MANAGER
}
