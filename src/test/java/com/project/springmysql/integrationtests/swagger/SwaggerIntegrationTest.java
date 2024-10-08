package com.project.springmysql.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.project.springmysql.springmysqlproject.SpringmysqlprojectApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.springmysql.configs.TestConfigs;
import com.project.springmysql.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(classes = SpringmysqlprojectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {
	
	@Test
	public void shouldDisplaySwaggerUiPage() {
		var content = 
		given()
		.basePath("/swagger-ui/index.html")
		.port(TestConfigs.SERVER_PORT)
		.when()
			.get()
		.then()
			.statusCode(200)
		.extract()
			.body().asString();
		
		assertTrue(content.contains("Swagger UI"));
	}
	
}
