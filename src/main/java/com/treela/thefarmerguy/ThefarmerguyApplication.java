package com.treela.thefarmerguy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ThefarmerguyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ThefarmerguyApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//registry.addMapping("/users/checklogin").allowedOrigins("http://localhost:8100");
				registry.addMapping("/**")
                                        .allowCredentials(true)
                                        .allowedHeaders("*")
                                        .allowedMethods("*")
                                        .allowedOrigins("*");
                                        //.allowedOrigins("http://localhost:8100");
			}
		};
	}
}