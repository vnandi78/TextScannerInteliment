package com.inteliment.mainApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableAutoConfiguration
@EnableResourceServer
@ComponentScan({"com.inteliment.restcontrollers", "com.inteliment.wordcounter.service"})
public class TextScannerIntelimentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextScannerIntelimentApplication.class, args);
	}
}
