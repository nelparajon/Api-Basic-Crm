package com.mic.crm.api_crm.controller;

import com.mic.crm.api_crm.dto.CustomerResponseApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CustomerControllerContextTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private HttpEntity<String> createEntity(String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer token123");
        return (jsonBody == null)
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(jsonBody, headers);
    }

    @Test
    void getAllCustomers(){
        // Crear un cliente para asegurar que la lista no esté vacía
        String createCustomer = """
            {
                "name": "Cliente getAll"
            }
            """;
        testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(createCustomer),
                CustomerResponseApi.class
        );

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
                "name": "Camara Comercio Oviedo"
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
        String createCustomer = """
                {
                    "name": "Cliente Por ID"
                }
                """;
        ResponseEntity<CustomerResponseApi> createResponse = testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(createCustomer),
                CustomerResponseApi.class
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        Long customerId = createResponse.getBody().getCustomerDto().getId();

        ResponseEntity<CustomerResponseApi> response = testRestTemplate.exchange(
                "/customers/{id}",
                HttpMethod.GET,
                createEntity(null),
                CustomerResponseApi.class,
                customerId
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getCustomerDto());
    }

    @Test
    void getCustomerByName() {
        String customerName = "Cliente Por Nombre";
        String createCustomer = """
                {
                    "name": "%s"
                }
                """.formatted(customerName);

        testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(createCustomer),
                CustomerResponseApi.class
        );

        ResponseEntity<CustomerResponseApi> response = testRestTemplate.exchange(
                "/customers/get-by-name/{name}",
                HttpMethod.GET,
                createEntity(null),
                CustomerResponseApi.class,
                customerName
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getCustomerDto());
        assertEquals(customerName, response.getBody().getCustomerDto().getName());
    }

    @Test
    void updateCustomer() {
        String createCustomer = """
                {
                    "name": "Cliente Para Actualizar"
                }
                """;
        ResponseEntity<CustomerResponseApi> createResponse = testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(createCustomer),
                CustomerResponseApi.class
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        Long customerId = createResponse.getBody().getCustomerDto().getId();

        String updateCustomer = """
                {
                    "name": "Nombre Actualizado"
                }
                """;
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
        assertEquals("Nombre Actualizado", response.getBody().getCustomerDto().getName());
    }

    @Test
    void deleteCustomer() {
        String customerName = "Cliente Para Eliminar";
        String createCustomer = """
                {
                    "name": "%s"
                }
                """.formatted(customerName);

        testRestTemplate.exchange(
                "/customers/create",
                HttpMethod.POST,
                createEntity(createCustomer),
                CustomerResponseApi.class
        );

        ResponseEntity<CustomerResponseApi> deleteResponse = testRestTemplate.exchange(
                "/customers/delete-by-name/{name}",
                HttpMethod.DELETE,
                createEntity(null),
                CustomerResponseApi.class,
                customerName
        );
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertNotNull(deleteResponse.getBody());
        assertEquals("Cliente eliminado con éxito.", deleteResponse.getBody().getMessage());
    }
}
