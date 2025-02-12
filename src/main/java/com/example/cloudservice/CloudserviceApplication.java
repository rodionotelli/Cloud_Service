package com.example.cloudservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudserviceApplication {
private static final Logger log = LogManager.getLogger(CloudserviceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CloudserviceApplication.class, args);
		log.info("App is running");
	}

}
