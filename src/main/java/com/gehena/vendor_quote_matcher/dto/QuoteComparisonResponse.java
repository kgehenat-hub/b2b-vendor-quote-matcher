package com.gehena.vendor_quote_matcher.dto;

import java.util.List;

public class QuoteComparisonResponse {

    private Double sourceQuoteTotal;
    private Double targetQuoteTotal;
    private Double potentialSavings;
    private String cheaperQuote;
    private List<PriceComparisonResponse> items;

    public QuoteComparisonResponse(
            Double sourceQuoteTotal,
            Double targetQuoteTotal,
            Double potentialSavings,
            String cheaperQuote,
            List<PriceComparisonResponse> items) {

        this.sourceQuoteTotal = sourceQuoteTotal;
        this.targetQuoteTotal = targetQuoteTotal;
        this.potentialSavings = potentialSavings;
        this.cheaperQuote = cheaperQuote;
        this.items = items;
    }

    public Double getSourceQuoteTotal() {
        return sourceQuoteTotal;
    }

    public Double getTargetQuoteTotal() {
        return targetQuoteTotal;
    }

    public Double getPotentialSavings() {
        return potentialSavings;
    }

    public String getCheaperQuote() {
        return cheaperQuote;
    }

    public List<PriceComparisonResponse> getItems() {
        return items;
    }
}