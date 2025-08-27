package com.example.webfluxdemo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Table("user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends BaseEntity<Long> {
    private Long userId;
    private Long roleId;
}
