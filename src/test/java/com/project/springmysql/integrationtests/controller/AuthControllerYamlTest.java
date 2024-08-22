package com.project.springmysql.integrationtests.controller;
import com.project.springmysql.configs.TestConfigs;
import com.project.springmysql.integrationtests.testcontainers.AbstractIntegrationTest;
import com.project.springmysql.integrationtests.vo.AccountCredentialsDTO;
import com.project.springmysql.mapper.YMLMapper;
import com.project.springmysql.springmysqlproject.SpringmysqlprojectApplication;
import com.project.springmysql.springmysqlproject.dto.security.TokenDTO;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SpringmysqlprojectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {


    private static YMLMapper objectMapper;
    private static TokenDTO tokenDTO;

    @BeforeAll
    public static void setUp() {
        objectMapper = new YMLMapper();

    }

    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsDTO user = new AccountCredentialsDTO("leandro", "coffee123");

        tokenDTO =
                given().config(RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                        .accept(TestConfigs.CONTENT_TYPE_YML)
                        .basePath("/auth/signin")
                        .port(TestConfigs.SERVER_PORT)
                        .contentType(TestConfigs.CONTENT_TYPE_YML)
                        .body(user, objectMapper)
                        .when()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body().as(TokenDTO.class, objectMapper);

        assertNotNull(tokenDTO.getAccessToken());
        assertNotNull(tokenDTO.getRefreshToken());

    }


    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsDTO user = new AccountCredentialsDTO("leandro", "coffee123");

        var newTokenDTO =
                given().config(RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                        .accept(TestConfigs.CONTENT_TYPE_YML)
                        .basePath("/auth/refresh")
                        .port(TestConfigs.SERVER_PORT)
                        .contentType(TestConfigs.CONTENT_TYPE_JASON)
                        .pathParam("username", tokenDTO.getUsername())
                        .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.getRefreshToken())
                        .when()
                        .put("{username}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body().as(TokenDTO.class, objectMapper);

        assertNotNull(newTokenDTO.getAccessToken());
        assertNotNull(newTokenDTO.getRefreshToken());

    }

}

