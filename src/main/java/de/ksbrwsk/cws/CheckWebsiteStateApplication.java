package de.ksbrwsk.cws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ApplicationProperties.class)
public class CheckWebsiteStateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckWebsiteStateApplication.class, args);
	}

}
