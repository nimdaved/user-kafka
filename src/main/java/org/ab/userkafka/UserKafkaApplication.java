package org.ab.userkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class UserKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserKafkaApplication.class, args);
	}

}
