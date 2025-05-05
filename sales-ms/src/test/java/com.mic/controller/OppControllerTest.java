package com.mic.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mic.dto.OppDto;
import com.mic.dto.OppResponseApi;
import com.mic.utils.OppPriority;
import com.mic.utils.OppState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OppControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private HttpEntity<String> createEntity(String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer ");
        return (jsonBody == null)
                ? new HttpEntity<>(headers)
                : new HttpEntity<>(jsonBody, headers);

    }
    @BeforeEach
    void setUp() {
        // Aquí puedes añadir datos de inicialización si es necesario.
    }

    @Test
    void shouldCreateOpportunity_whenValidOppDtoProvided() {
        // Arrange
        OppDto oppDto = OppDto.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(30))
                .customerId(1L)
                .contactId(2L)
                .assignedUserId("user123")
                .notes("Oportunidad web")
                .build();

        // Realiza la petición POST
        ResponseEntity<OppResponseApi> response = restTemplate.postForEntity("/opportunities", oppDto, OppResponseApi.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Oportunidad de Venta Creada", response.getBody().getMessage());
        assertEquals("Web", response.getBody().getOppDto().getTitle());
    }

    @Test
    void shouldReturnAllOpportunities_whenOpportunitiesExist() {
        // Arrange
        OppDto oppDto = OppDto.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(30))
                .customerId(1L)
                .contactId(2L)
                .assignedUserId("user123")
                .notes("Oportunidad web")
                .build();

        // Crear una oportunidad usando POST
        restTemplate.postForEntity("/opportunities", oppDto, OppResponseApi.class);

        // Act & Assert: Verifica que todas las oportunidades existan con GET
        ResponseEntity<OppResponseApi> response = restTemplate.getForEntity("/opportunities", OppResponseApi.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Opportunities founded", response.getBody().getMessage());
        assertTrue(response.getBody().getOpps() != null);
        List<OppDto> opps = (List<OppDto>) response.getBody().getOpps();
        assertFalse(opps.isEmpty());
        assertEquals("Web", opps.get(0).getTitle());
    }

    @Test
    void shouldUpdateOpportunity_whenValidPublicIdAndOppDtoProvided() {
        // Arrange
        String publicId = "validPublicId";
        String json = """
{
    "title": "Updated Web",
    "description": "Updated Página Web",
    "state": "NUEVA",
    "priority": "ALTA",
    "amount": 2000.0,
    "startDate": "2025-05-02T14:30:00+00:00",  // Ajusta la fecha y hora según el momento actual
    "endDate": "2025-06-01T14:30:00+00:00",    // Ajusta la fecha y hora según el momento actual
    "customerId": 1,
    "contactId": 2,
    "assignedUserId": "user123",
    "notes": "Actualización de la oportunidad"
}
""";

        // Llamada PUT
        ResponseEntity<OppResponseApi> response = restTemplate.exchange(
                "/opportunities/{publicId}",
                HttpMethod.PUT,
                createEntity(json),
                OppResponseApi.class,
                publicId

        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Opportunidad de venta actualizada con éxito", response.getBody().getMessage());
    }

    @Test
    void shouldDeleteOpportunity_whenValidPublicIdProvided() {
        // Arrange
        String publicId = "validPublicId";

        // Verifica la respuesta después de la eliminación
        ResponseEntity<OppResponseApi> response = restTemplate.exchange(
                "/opportunities/{publicId}",
                HttpMethod.DELETE,
                null,
                OppResponseApi.class,
                publicId
        );

        // Imprime la respuesta para verificar que es lo esperado
        System.out.println(response.getBody());

        // Verifica que la respuesta tenga el mensaje esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Opportunidad eliminada con éxito", response.getBody().getMessage());
    }

    @Test
    void shouldThrowOpportunityNotFoundException_whenInvalidPublicIdForDeleteProvided() {
        // Arrange
        String publicId = "invalidPublicId";

        // Act & Assert
        ResponseEntity<OppResponseApi> response = restTemplate.exchange(
                "/opportunities/{publicId}",
                HttpMethod.DELETE,
                null,
                OppResponseApi.class,
                publicId
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Opportunity with public ID: " + publicId + " not found", response.getBody().getMessage());
    }
}
