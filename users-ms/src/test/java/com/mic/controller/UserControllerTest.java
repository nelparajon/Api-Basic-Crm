package com.mic.controller;

import com.mic.dto.ApiResponse;
import com.mic.dto.UserResponseDto;
import org.glassfish.jaxb.core.v2.TODO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private HttpEntity<String> createEntity(String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer ");
        return (jsonBody == null)
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(jsonBody, headers);
    }

    @Test
    void registerUserTest() {
        String createCustomerJsonBody = """
            {
                "firstName": "Cliff",
                "lastName": "Burton",
                "email": "cb@example.es",
                "password": "123456",
                "role": "ROLE_ADMIN"
            }
            """;

        ResponseEntity<ApiResponse> response = testRestTemplate.exchange(
                "/users/register",
                HttpMethod.POST,
                createEntity(createCustomerJsonBody),
                ApiResponse.class
        );

        System.out.println("STATUS: " + response.getStatusCode());
        System.out.println("BODY: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuario creado con éxito", response.getBody().getMessage());
    }

   //TODO: test para el update
}
