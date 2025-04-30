package com.mic.crm.api_crm.controller;

import com.mic.crm.api_crm.dto.CustomerResponseApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerContextTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpEntity<String> entity;

    private HttpEntity<String> createEntity(String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer token123");
        return (jsonBody == null)
                ? new HttpEntity<>(headers)
                :new HttpEntity<>(jsonBody, headers);
    }

    @Test
    void getAllCustomers(){

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                "/customers",
                HttpMethod.GET,
                createEntity(null),
                String.class
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createCustomer(){
        String jsonBody = """
                {
                "name": "Camara Comercio"
                }
                """;
        ResponseEntity<CustomerResponseApi> response = testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(jsonBody),
                CustomerResponseApi.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getCustomerDto());
    }

    @Test
    void getCustomerById() {
        // Suponiendo que tienes un cliente con id 1 en tu base de datos de prueba
        long customerId = 1L;
        ResponseEntity<CustomerResponseApi> response = testRestTemplate.exchange(
                "/customers/" + customerId,
                HttpMethod.GET,
                createEntity(null),
                CustomerResponseApi.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getCustomerDto());
    }

    // Test para obtener un cliente por nombre
    @Test
    void getCustomerByName() {

        String customerName = "Camara Comercio";
        ResponseEntity<CustomerResponseApi> response = testRestTemplate.exchange(
                "/customers/get-by-name/{name}" ,
                HttpMethod.GET,
                createEntity(null),
                CustomerResponseApi.class,
                customerName
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getCustomerDto());
        assertEquals("Camara Comercio", response.getBody().getCustomerDto().getName());
    }

    // Test para actualizar un cliente
    @Test
    void updateCustomer() {
        // Suponiendo que tienes un cliente con id 1 en tu base de datos de prueba
        String createCustomer = """
                {
                    "name": "Camara de Comercio"
                }
                """;
        ResponseEntity<CustomerResponseApi> createResponse = testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(createCustomer),
                CustomerResponseApi.class
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        String updateCustomer = """
                {
                    "name": "Camara de comercio Actualizada"
                }
                """;
        Long customerId = createResponse.getBody().getCustomerDto().getId();
        ResponseEntity<CustomerResponseApi> response = testRestTemplate.exchange(
                "/customers/{id}",
                HttpMethod.PUT,
                createEntity(updateCustomer),
                CustomerResponseApi.class,
                customerId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getCustomerDto());
        assertEquals(customerId, response.getBody().getCustomerDto().getId());

    }

    // Test para eliminar un cliente por nombre
    @Test
    void deleteCustomer() {
        // Suponiendo que tienes un cliente con el nombre "Camara Comercio" en tu base de datos de prueba
        String createCustomer = """
                {
                    "name": "Camara Comercio"
                }
                """;
        ResponseEntity<CustomerResponseApi> createResponse = testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(createCustomer),
                CustomerResponseApi.class
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        String customerName = createResponse.getBody().getCustomerDto().getName();
        ResponseEntity<CustomerResponseApi> response = testRestTemplate.exchange(
                "/customers/{name}",
                HttpMethod.DELETE,
                createEntity(null),
                CustomerResponseApi.class,
                customerName
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Cliente eliminado con éxito.", response.getBody().getMessage());
    }

}