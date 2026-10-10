package com.gehena.vendor_quote_matcher.dto;

public class ProductMatchResponse {

    private Long sourceItemId;
    private String matchedProduct;
    private Double matchedUnitPrice;
    private Long matchedItemId;
    private Double cosineDistance;

    public ProductMatchResponse(
            Long sourceItemId,
            String matchedProduct,
            Double matchedUnitPrice,
            Long matchedItemId,
            Double cosineDistance) {

        this.sourceItemId = sourceItemId;
        this.matchedProduct = matchedProduct;
        this.matchedUnitPrice = matchedUnitPrice;
        this.matchedItemId = matchedItemId;
        this.cosineDistance = cosineDistance;
    }

    public Long getSourceItemId() {
        return sourceItemId;
    }

    public String getMatchedProduct() {
        return matchedProduct;
    }

    public Double getMatchedUnitPrice() {
        return matchedUnitPrice;
    }

    public Long getMatchedItemId() {
        return matchedItemId;
    }

    public Double getCosineDistance() {
        return cosineDistance;
    }
}