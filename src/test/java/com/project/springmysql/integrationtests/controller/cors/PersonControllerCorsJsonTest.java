package com.project.springmysql.integrationtests.controller.cors;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringmysqlprojectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerCorsJsonTest extends AbstractIntegrationTest {

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
						.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LUCIO)
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

		Assertions.assertEquals("Richard", createdUser.getName());
		Assertions.assertEquals("richard@mail.com", createdUser.getEmail());
		Assertions.assertEquals("9289211982", createdUser.getPhoneNumber());
	}

	private void mockPerson() {
		person.setName("Richard");
		person.setEmail("richard@mail.com");
		person.setPhoneNumber("9289211982");
	}

	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws IOException {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JASON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
						.body(person)
						.when()
						.post()
						.then()
						.statusCode(403)
						.extract()
						.body().asString();


		Assertions.assertNotNull(content);
		Assertions.assertEquals("Invalid CORS request", content);

	}
	@Test
	@Order(3)
	public void testFindById() throws IOException {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JASON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LUCIO)
						.pathParam("id", person.getId())
						.when()
						.get("{id}")
						.then()
						.statusCode(200)
						.extract()
						.body().asString();

		PersonDTO persistedUser = objectMapper.readValue(content, PersonDTO.class);
		person = persistedUser;
		Assertions.assertNotNull(persistedUser);

		assertTrue(persistedUser.getId() > 0);

		Assertions.assertNotNull(persistedUser);

		Assertions.assertNotNull(persistedUser.getId());
		Assertions.assertNotNull(persistedUser.getName());
		Assertions.assertNotNull(persistedUser.getEmail());
		Assertions.assertNotNull(persistedUser.getPhoneNumber());

		Assertions.assertEquals("Richard", persistedUser.getName());
		Assertions.assertEquals("richard@mail.com", persistedUser.getEmail());
		Assertions.assertEquals("9289211982", persistedUser.getPhoneNumber());
	}

	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws IOException {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JASON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
						.pathParam("id", person.getId())
						.when()
						.get("{id}")
						.then()
						.statusCode(403)
						.extract()
						.body().asString();

		Assertions.assertNotNull(content);
		Assertions.assertEquals("Invalid CORS request", content);

	}

}