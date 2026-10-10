package com.gehena.vendor_quote_matcher.dto;

public interface ProductMatchProjection {

    Long getMatchedItemId();

    String getMatchedProduct();

    Double getMatchedUnitPrice();

    Double getCosineDistance();
}