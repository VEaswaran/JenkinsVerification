package com.example.sku.controller;

import com.example.sku.dto.SkuResponse;
import com.example.sku.service.SkuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing SKU lookup endpoints.
 */
@RestController
@RequestMapping("/api/sku")
public class SkuController {

    private final SkuService skuService;

    public SkuController(SkuService skuService) {
        this.skuService = skuService;
    }

    @GetMapping("/{skuId}")
    public ResponseEntity<SkuResponse> getSkuById(@PathVariable String skuId) {
        SkuResponse response = skuService.getSkuDetails(skuId);
        if (!response.isFound()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }
}

