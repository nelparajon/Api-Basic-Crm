package controller;

import com.mic.repository.ProductRepository;
import io.netty.handler.codec.http2.Http2Headers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsControllerTest {

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

        return new HttpEntity<>(jsonBody, headers);
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
                "/products-service/products",
                HttpMethod.GET,
                createEntity(jsonBody),
                String.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}


