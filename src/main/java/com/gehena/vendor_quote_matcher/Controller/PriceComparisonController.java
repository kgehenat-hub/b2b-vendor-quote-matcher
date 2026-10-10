package com.gehena.vendor_quote_matcher.Controller;

import com.gehena.vendor_quote_matcher.Service.PriceComparisonService;
import com.gehena.vendor_quote_matcher.dto.QuoteComparisonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/price-comparison")
public class PriceComparisonController {

    private final PriceComparisonService priceComparisonService;

    public PriceComparisonController(
            PriceComparisonService priceComparisonService) {

        this.priceComparisonService = priceComparisonService;
    }

    @GetMapping("/{sourceQuoteId}/{targetQuoteId}")
    public QuoteComparisonResponse compareQuotes(
            @PathVariable Long sourceQuoteId,
            @PathVariable Long targetQuoteId) {

        return priceComparisonService.compareQuotes(
                sourceQuoteId,
                targetQuoteId
        );
    }
}