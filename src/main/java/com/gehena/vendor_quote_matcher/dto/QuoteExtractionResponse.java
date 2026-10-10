package com.gehena.vendor_quote_matcher.dto;

import java.util.List;

public class QuoteExtractionResponse {

    private String vendorName;
    private String email;
    private String companyName;
    private List<QuoteItemDTO> items;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<QuoteItemDTO> getItems() {
        return items;
    }

    public void setItems(List<QuoteItemDTO> items) {
        this.items = items;
    }
}