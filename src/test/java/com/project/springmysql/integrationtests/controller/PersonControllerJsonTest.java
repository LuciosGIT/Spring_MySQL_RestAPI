package com.project.springmysql.integrationtests.controller;

import com.project.springmysql.configs.TestConfigs;
import com.project.springmysql.integrationtests.testcontainers.AbstractIntegrationTest;
import com.project.springmysql.integrationtests.vo.AccountCredentialsDTO;
import com.project.springmysql.integrationtests.vo.PersonDTO;
import com.project.springmysql.integrationtests.vo.TokenDTO;
import com.project.springmysql.springmysqlproject.SpringmysqlprojectApplication;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringmysqlprojectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PersonDTO person;

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonDTO();
	}


	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO user = new AccountCredentialsDTO("leandro", "coffee123");

		var accessToken =
				given().basePath("/auth/signin")
						.port(TestConfigs.SERVER_PORT)
						.contentType(TestConfigs.CONTENT_TYPE_JASON)
						.body(user)
						.when()
						.post()
						.then()
						.statusCode(200)
						.extract()
						.body().as(TokenDTO.class).getAccessToken();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws IOException {
		mockPerson();

		var content =
				given().spec(specification)
						.contentType(TestConfigs.CONTENT_TYPE_JASON)
						.body(person)
						.when()
						.post()
						.then()
						.statusCode(201)
						.extract()
						.body().asString();

		PersonDTO createdUser = objectMapper.readValue(content, PersonDTO.class);
		person = createdUser;
		Assertions.assertNotNull(createdUser);

		assertTrue(createdUser.getId() > 0);

		Assertions.assertNotNull(createdUser);

		Assertions.assertNotNull(createdUser.getId());
		Assertions.assertNotNull(createdUser.getName());
		Assertions.assertNotNull(createdUser.getEmail());
		Assertions.assertNotNull(createdUser.getPhoneNumber());
		Assertions.assertNotNull(createdUser.getEnabled());

		assertEquals("Nelson", createdUser.getName());
		assertEquals("Nelson@mail.com", createdUser.getEmail());
		assertEquals("924728823", createdUser.getPhoneNumber());
		assertEquals(false, createdUser.getEnabled());
	}

	private void mockPerson() {
		person.setName("Nelson");
		person.setEmail("Nelson@mail.com");
		person.setPhoneNumber("924728823");
		person.setEnabled(false);
	}

}