package com.example.sku;

import com.example.sku.dto.SkuResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SkuApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void contextLoadsAndReturnsSkuDetails() {
        ResponseEntity<SkuResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/sku/SKU1001", SkuResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() != null && response.getBody().isFound());
    }

    @Test
    void returnsNotFoundForUnknownSku() {
        ResponseEntity<SkuResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/sku/SKU_UNKNOWN", SkuResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

