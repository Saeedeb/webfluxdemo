package com.example.webfluxdemo;

import com.example.webfluxdemo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Predicate;

@SpringBootApplication
public class WebfluxdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxdemoApplication.class, args);

		Predicate<User> predicat=(user)-> user.getId() == 1 ;
	}

}
