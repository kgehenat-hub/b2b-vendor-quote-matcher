package com.gehena.vendor_quote_matcher.dto;

public class PriceComparisonResponse {

    private Long sourceItemId;
    private String sourceProduct;
    private Double sourceUnitPrice;

    private Long matchedItemId;
    private String matchedProduct;
    private Double matchedUnitPrice;

    private Double priceDifference;
    private String cheaperOption;

    public PriceComparisonResponse(
            Long sourceItemId,
            String sourceProduct,
            Double sourceUnitPrice,
            Long matchedItemId,
            String matchedProduct,
            Double matchedUnitPrice,
            Double priceDifference,
            String cheaperOption) {

        this.sourceItemId = sourceItemId;
        this.sourceProduct = sourceProduct;
        this.sourceUnitPrice = sourceUnitPrice;
        this.matchedItemId = matchedItemId;
        this.matchedProduct = matchedProduct;
        this.matchedUnitPrice = matchedUnitPrice;
        this.priceDifference = priceDifference;
        this.cheaperOption = cheaperOption;
    }

    public Long getSourceItemId() {
        return sourceItemId;
    }

    public String getSourceProduct() {
        return sourceProduct;
    }

    public Double getSourceUnitPrice() {
        return sourceUnitPrice;
    }

    public Long getMatchedItemId() {
        return matchedItemId;
    }

    public String getMatchedProduct() {
        return matchedProduct;
    }

    public Double getMatchedUnitPrice() {
        return matchedUnitPrice;
    }

    public Double getPriceDifference() {
        return priceDifference;
    }

    public String getCheaperOption() {
        return cheaperOption;
    }
}