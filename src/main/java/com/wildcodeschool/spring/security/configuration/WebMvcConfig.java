package com.wildcodeschool.spring.security.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wildcodeschool.spring.security.persistence.entities.User;
import com.wildcodeschool.spring.security.persistence.enums.RoleEnum;
import com.wildcodeschool.spring.security.persistence.repositories.UserRepository;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Autowired
    public WebMvcConfig(UserRepository userRepository) {

        logger.info("Initializing users");
        userRepository.deleteAll();

        // Ceci n'est pas à recopier en production
        List<RoleEnum> userRole = List.of(RoleEnum.USER);
        List<RoleEnum> adminRole = List.of(RoleEnum.USER, RoleEnum.ADMINISTRATOR);
        User user = new User("user", "user", "User", "USER", userRole);
        User adminUser = new User("admin", "admin", "Admin", "ADMIN", adminRole);
        userRepository.save(user);
        userRepository.save(adminUser);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/auth").setViewName("auth/auth");
        registry.addViewController("/auth/admin").setViewName("auth/admin");
        registry.addViewController("/errorAlreadyConnected").setViewName("/errorAlreadyConnected");
        registry.addViewController("/errorAccessUnAuthorised").setViewName("/errorAccessUnAuthorised");
    }
}