package com.wildcodeschool.spring.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import com.wildcodeschool.spring.security.persistence.entities.User;
import com.wildcodeschool.spring.security.persistence.enums.RoleEnum;
import com.wildcodeschool.spring.security.persistence.repositories.UserRepository;

@SpringBootApplication
public class ExerciseApplication {

	@Autowired
	UserRepository repository;

	private static Logger logger = LoggerFactory.getLogger(ExerciseApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}

	@EventListener
	public void onStarted(ApplicationStartedEvent event) {
		logger.info("application started");

		// 2. Initialisation des données

		repository.deleteAll();

		User user = new User("john", "password", "John", "Doe", List.of(RoleEnum.USER));
		User admin = new User("axel", "password", "Axel", "Admin", List.of(RoleEnum.USER, RoleEnum.ADMINISTRATOR));

		repository.save(user);
		repository.save(admin);
	}
}
