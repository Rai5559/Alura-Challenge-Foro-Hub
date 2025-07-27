package com.rai69.Foro_Hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rai69.Foro_Hub.util.EnvLoader;

@SpringBootApplication
public class ForoHubApplication {

	public static void main(String[] args) {
		EnvLoader envLoader = new EnvLoader();
		SpringApplication.run(ForoHubApplication.class, args);
		System.out.println("Foro Hub Application has started successfully!");
	}

}
