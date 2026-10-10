package com.gehena.vendor_quote_matcher.Controller;

import com.gehena.vendor_quote_matcher.Service.ProductMatchingService;
import com.gehena.vendor_quote_matcher.dto.ProductMatchProjection;
import com.gehena.vendor_quote_matcher.dto.ProductMatchResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matching")
public class ProductMatchingController {

    private final ProductMatchingService productMatchingService;

    public ProductMatchingController(
            ProductMatchingService productMatchingService) {

        this.productMatchingService = productMatchingService;
    }

    @GetMapping("/{quoteItemId}/{targetQuoteId}")
    public ProductMatchResponse findBestMatch(
            @PathVariable Long quoteItemId,
            @PathVariable Long targetQuoteId) {

        ProductMatchProjection match =
                productMatchingService.findBestMatch(
                        quoteItemId,
                        targetQuoteId
                );

        return new ProductMatchResponse(
                quoteItemId,
                match.getMatchedProduct(),
                match.getMatchedUnitPrice(),
                match.getMatchedItemId(),
                match.getCosineDistance()
        );
    }
}