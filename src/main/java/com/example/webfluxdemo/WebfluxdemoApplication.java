package com.example.webfluxdemo;

import com.example.webfluxdemo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.function.Predicate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WebfluxdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxdemoApplication.class, args);

		Predicate<User> predicat=(user)-> user.getId() == 1 ;
	}

}
