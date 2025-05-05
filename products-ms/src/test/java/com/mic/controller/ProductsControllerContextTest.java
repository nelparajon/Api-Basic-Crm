package com.mic.controller;

import com.mic.dto.ResponseProductApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductsControllerContextTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpEntity<String> entity;

    @Test
    void setUp(){

    }

    private HttpEntity<String> createEntity (String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer token123");
        return (jsonBody == null)
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(jsonBody, headers);
    }

    @Test
    void getAllProducts(){
        String jsonBody = """
                {
                    "title": "Nuevo Producto",
                    "description": "Descripción de prueba",
                    "price": 150.0
                }
                """;
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                "/products",
                HttpMethod.GET,
                createEntity(null),
                String.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createProduct(){
        String jsonBody = """
                {
                "title": "Product Test",
                "description": "Descripción de prueba",
                 "price": 199.99
                }
                """;
        ResponseEntity<ResponseProductApi> response = testRestTemplate.exchange(
                "/products/create",
                HttpMethod.POST,
                createEntity(jsonBody),
                ResponseProductApi.class
        );
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getProductDto());
    }

    @Test
    void updateProduct(){
        String createProduct = """
                {
                "title": "Product Test",
                "description": "Descripción de prueba",
                 "price": 199.99
                }
                """;
        ResponseEntity<ResponseProductApi> createResponse =  testRestTemplate.exchange(
                "/products/create",
                HttpMethod.POST,
                createEntity(createProduct),
                ResponseProductApi.class
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        String jsonBody = """
                {
                "title": "Product Test update",
                "description": "Descripción de prueba",
                 "price": 100.00
                }
                """;
        Long productId = createResponse.getBody().getProductDto().getId();

        ResponseEntity<ResponseProductApi> response = testRestTemplate.exchange(
                "/products/{id}",
                HttpMethod.PUT,
                createEntity(jsonBody),
                ResponseProductApi.class,
                productId
        );
    }
}


