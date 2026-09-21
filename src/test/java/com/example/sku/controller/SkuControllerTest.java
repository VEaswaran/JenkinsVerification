package com.example.sku.controller;

import com.example.sku.dto.SkuResponse;
import com.example.sku.service.SkuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SkuControllerTest {

    @Mock
    private SkuService skuService;

    private SkuController skuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        skuController = new SkuController(skuService);
    }

    @Test
    void returnsOkWhenSkuIsFound() {
        SkuResponse mockedResponse = new SkuResponse("SKU1001", "Wireless Mouse", new BigDecimal("19.99"), 150, true);
        when(skuService.getSkuDetails("SKU1001")).thenReturn(mockedResponse);

        ResponseEntity<SkuResponse> result = skuController.getSkuById("SKU1001");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockedResponse, result.getBody());
        verify(skuService).getSkuDetails("SKU1001");
    }

    @Test
    void returnsNotFoundWhenSkuIsMissing() {
        SkuResponse mockedResponse = SkuResponse.notFound("SKU9999");
        when(skuService.getSkuDetails("SKU9999")).thenReturn(mockedResponse);

        ResponseEntity<SkuResponse> result = skuController.getSkuById("SKU9999");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(mockedResponse, result.getBody());
    }
}

