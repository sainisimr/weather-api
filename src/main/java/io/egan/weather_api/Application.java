package io.egan.weather_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.egan.weather_api.Config.SwaggerConfig;
import io.egan.weather_api.Config.WebConfig;


@Import({SwaggerConfig.class, WebConfig.class})
@SpringBootApplication
public class Application {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}

}
