package com.example.sku.service;

import com.example.sku.dto.SkuResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkuServiceImplTest {

    private final SkuServiceImpl skuService = new SkuServiceImpl();

    @Test
    void returnsSkuDetailsWhenSkuExists() {
        SkuResponse response = skuService.getSkuDetails("SKU1001");

        assertTrue(response.isFound());
        assertEquals("SKU1001", response.getSkuId());
        assertEquals("Wireless Mouse", response.getProductName());
    }

    @Test
    void returnsSkuDetailsCaseInsensitively() {
        SkuResponse response = skuService.getSkuDetails("sku1002");

        assertTrue(response.isFound());
        assertEquals("SKU1002", response.getSkuId());
    }

    @Test
    void returnsNotFoundWhenSkuIsUnknown() {
        SkuResponse response = skuService.getSkuDetails("SKU9999");

        assertFalse(response.isFound());
        assertEquals("SKU9999", response.getSkuId());
    }

    @Test
    void returnsNotFoundWhenSkuIsBlank() {
        SkuResponse response = skuService.getSkuDetails("   ");

        assertFalse(response.isFound());
    }

    @Test
    void returnsNotFoundWhenSkuIsNull() {
        SkuResponse response = skuService.getSkuDetails(null);

        assertFalse(response.isFound());
    }
}

