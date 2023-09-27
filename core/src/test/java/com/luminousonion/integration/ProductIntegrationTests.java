package com.luminousonion.integration;

import com.luminousonion.LuminousOnionApplication;
import com.luminousonion.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LuminousOnionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTests {

    private final String url = "http://localhost:";
    private final String endpoint = "/api/product";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenANewProduct_whenPostOverAPI_thenReturnCreatedStatusCode() {
        Product testProduct = new Product();
        testProduct.setName("Test");

        ResponseEntity<String> responseEntity = this.restTemplate.
                postForEntity(url + port + endpoint, testProduct, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

}
