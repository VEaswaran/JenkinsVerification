package com.example.sku.dto;

import java.math.BigDecimal;

/**
 * Response payload representing SKU details.
 */
public class SkuResponse {

    private String skuId;
    private String productName;
    private BigDecimal price;
    private int quantityAvailable;
    private boolean found;

    public SkuResponse() {
    }

    public SkuResponse(String skuId, String productName, BigDecimal price, int quantityAvailable, boolean found) {
        this.skuId = skuId;
        this.productName = productName;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.found = found;
    }

    public static SkuResponse notFound(String skuId) {
        return new SkuResponse(skuId, null, null, 0, false);
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}

