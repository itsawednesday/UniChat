package com.mapchat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mapchat.server.model.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)

/**
 * Starts the server
 * 
 *
 */
public class MapChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapChatApplication.class, args);
	}

}
