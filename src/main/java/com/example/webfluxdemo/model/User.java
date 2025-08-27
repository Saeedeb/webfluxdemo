package com.example.webfluxdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;



@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class User extends BaseEntity<Long>  {
    public User(Long Long,String username, String name, String email, String password, String status) {
        super();
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    private String username;
    private String name;
    private String email;
    private String password;
    private String status;

    @Transient
    private List<Role> roles;





}
