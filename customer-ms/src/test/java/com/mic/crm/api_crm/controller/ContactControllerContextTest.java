package com.mic.crm.api_crm.controller;

import com.mic.crm.api_crm.dto.ContactDto;
import com.mic.crm.api_crm.dto.ContactResponseApi;
import com.mic.crm.api_crm.dto.CustomerResponseApi;
import com.mic.crm.api_crm.model.Contact;
import com.mic.crm.api_crm.model.Customer;
import com.mic.crm.api_crm.repository.ContactRepository;
import com.mic.crm.api_crm.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerContextTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpEntity<String> entity;
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private HttpEntity<String> createEntity(String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer token123");
        return (jsonBody == null)
                ? new HttpEntity<>(headers)
                :new HttpEntity<>(jsonBody, headers);
    }
    @Test
    void createContact(){
        Customer customer = Customer.builder()
                .id(1L)
                .name("Camara Comercio")
                .registrationDate(OffsetDateTime.now())
                .contactos(Collections.emptyList()).build();

        String jsonBody = """
                {
                    "contactFirstName": "Pedro",
                    "contactSecondName": "Díaz",
                    "email": "pd@exampl.es",
                    "phone": "1234567",
                    "cargo": "Director",
                    "customerId": 1
                }
                """;
        ResponseEntity<ContactResponseApi> responseEntity = testRestTemplate.exchange(
                "/contacts/create",
                HttpMethod.POST,
                createEntity(jsonBody),
                ContactResponseApi.class
        );
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Pedro", responseEntity.getBody().getContactDto().getContactFirstName());

    }
    @Test
    void updateContact() {
        // Crear un cliente para asociarlo al contacto
        Customer customer = Customer.builder()
                        .name("Camara Comercio").build();
        customerRepository.save(customer);

        // Crear un contacto con el customerId
        String jsonBody = """
                {
                    "contactFirstName": "Pedro",
                    "contactSecondName": "Díaz",
                    "email": "pd@exampl.es",
                    "phone": "1234567",
                    "cargo": "Director",
                    "customerId": 1
                }
                """;
        ResponseEntity<ContactResponseApi> createResponse = testRestTemplate.exchange(
                "/contacts/create",
                HttpMethod.POST,
                createEntity(jsonBody),
                ContactResponseApi.class
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getContactDto());

        // Obtener el ID del contacto creado
        long contactId = createResponse.getBody().getContactDto().getId();

        // Crear el DTO actualizado para el contacto
        ContactDto updateContactDto = new ContactDto("Pedro", "Díaz", "pedro@ejemplo.com", "987654", "IT", "Manager", customer.getId());

        // Llamar al endpoint PUT para actualizar el contacto
        ResponseEntity<ContactResponseApi> updateResponse = testRestTemplate.exchange(
                "/contacts/{contactId}",
                HttpMethod.PUT,
                createEntity(updateContactDto.toString()),
                ContactResponseApi.class,
                contactId
        );

        // Verificar que la respuesta sea correcta
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody().getContactDto());
        assertEquals(contactId, updateResponse.getBody().getContactDto().getId());
        assertEquals("Pedro", updateResponse.getBody().getContactDto().getContactFirstName());
        assertEquals("Díaz", updateResponse.getBody().getContactDto().getContactSecondName());
        assertEquals("pedro@ejemplo.com", updateResponse.getBody().getContactDto().getEmail());

        // Verificar que los cambios se hayan aplicado en la base de datos
        Contact updatedContact = contactRepository.findById(contactId).orElseThrow(() -> new RuntimeException("Contacto no encontrado"));
        assertEquals("Pedro", updatedContact.getContactFirstName());
        assertEquals("Díaz", updatedContact.getContactSecondName());
        assertEquals("pedro@ejemplo.com", updatedContact.getEmail());
    }
}

