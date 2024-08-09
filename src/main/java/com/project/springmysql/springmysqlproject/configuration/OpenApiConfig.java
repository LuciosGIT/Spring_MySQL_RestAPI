package com.project.springmysql.springmysqlproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTful API with Java 21 and SpringBoot 3")
						.version("v1").description("User RESTful API")
						.termsOfService("https://www.linkedin.com/in/jluciojunior/")
						.license(new License().name("Apache 2.0")
						.url("https://www.linkedin.com/in/jluciojunior/")));
	}
}
