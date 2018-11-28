package com.hznu.fa2login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Fa2loginApplication {

	public static void main(String[] args) {
		SpringApplication.run(Fa2loginApplication.class, args);
	}
}
