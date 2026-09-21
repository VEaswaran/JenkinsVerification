package com.example.sku.service;

import com.example.sku.dto.SkuResponse;

/**
 * Service contract for SKU lookups.
 */
public interface SkuService {

    /**
     * Returns mocked SKU details for the given SKU identifier.
     *
     * @param skuId the SKU identifier to look up
     * @return the SKU details, or a "not found" response if the SKU is unknown/blank
     */
    SkuResponse getSkuDetails(String skuId);
}

