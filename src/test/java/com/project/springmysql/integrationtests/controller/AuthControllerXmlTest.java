package com.project.springmysql.integrationtests.controller;


import com.project.springmysql.configs.TestConfigs;
import com.project.springmysql.integrationtests.testcontainers.AbstractIntegrationTest;
import com.project.springmysql.integrationtests.vo.AccountCredentialsDTO;
import com.project.springmysql.springmysqlproject.SpringmysqlprojectApplication;
import com.project.springmysql.springmysqlproject.dto.security.TokenDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SpringmysqlprojectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {

    private static TokenDTO tokenDTO;

    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsDTO user = new AccountCredentialsDTO("leandro", "coffee123");

        tokenDTO =
                given().basePath("/auth/signin")
                        .port(TestConfigs.SERVER_PORT)
                        .contentType(TestConfigs.CONTENT_TYPE_XML)
                        .body(user)
                        .when()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body().as(TokenDTO.class);

        assertNotNull(tokenDTO.getAccessToken());
        assertNotNull(tokenDTO.getRefreshToken());

    }


    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsDTO user = new AccountCredentialsDTO("leandro", "coffee123");

        var newtokenDTO =
                given().basePath("/auth/refresh")
                        .port(TestConfigs.SERVER_PORT)
                        .contentType(TestConfigs.CONTENT_TYPE_JASON)
                        .pathParam("username", tokenDTO.getUsername())
                        .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.getRefreshToken())
                        .when()
                        .put("{username}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body().as(TokenDTO.class);

        assertNotNull(newtokenDTO.getAccessToken());
        assertNotNull(newtokenDTO.getRefreshToken());

    }

}
