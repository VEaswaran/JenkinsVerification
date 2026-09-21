package com.example.sku.service;

import com.example.sku.dto.SkuResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mocked implementation of {@link SkuService} backed by an in-memory map.
 * Simulates a real service layer without requiring an external data source.
 */
@Service
public class SkuServiceImpl implements SkuService {

    private final Map<String, SkuResponse> mockedSkuStore = new ConcurrentHashMap<>();

    public SkuServiceImpl() {
        mockedSkuStore.put("SKU1001", new SkuResponse("SKU1001", "Wireless Mouse", new BigDecimal("19.99"), 150, true));
        mockedSkuStore.put("SKU1002", new SkuResponse("SKU1002", "Mechanical Keyboard", new BigDecimal("79.99"), 75, true));
        mockedSkuStore.put("SKU1003", new SkuResponse("SKU1003", "27-inch Monitor", new BigDecimal("249.99"), 30, true));
    }

    @Override
    public SkuResponse getSkuDetails(String skuId) {
        if (skuId == null || skuId.isBlank()) {
            return SkuResponse.notFound(skuId);
        }

        SkuResponse response = mockedSkuStore.get(skuId.toUpperCase());
        if (response == null) {
            return SkuResponse.notFound(skuId);
        }
        return response;
    }
}

