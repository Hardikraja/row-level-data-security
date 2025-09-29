package com.data.security.demo;

import com.blazebit.persistence.spring.data.impl.repository.BlazePersistenceRepositoryFactoryBean;
import com.data.security.demo.utils.CurrentLoggedInUsersContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CurrentLoggedInUsersContext setInitCurrentLoggedInUserContextAsSuperAdmin(){
		return new CurrentLoggedInUsersContext(1L,1L);
	}



}
