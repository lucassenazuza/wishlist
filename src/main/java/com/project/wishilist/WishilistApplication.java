package com.project.wishilist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class WishilistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishilistApplication.class, args);
	}

}
