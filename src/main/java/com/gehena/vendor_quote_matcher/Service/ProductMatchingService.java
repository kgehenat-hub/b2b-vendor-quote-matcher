package com.gehena.vendor_quote_matcher.Service;

import com.gehena.vendor_quote_matcher.Repository.ProductEmbeddingRepository;
import com.gehena.vendor_quote_matcher.dto.ProductMatchProjection;
import org.springframework.stereotype.Service;

@Service
public class ProductMatchingService {

    private static final double MATCH_THRESHOLD = 0.30;

    private final ProductEmbeddingRepository productEmbeddingRepository;

    public ProductMatchingService(
            ProductEmbeddingRepository productEmbeddingRepository) {

        this.productEmbeddingRepository = productEmbeddingRepository;
    }

    public ProductMatchProjection findBestMatch(
            Long sourceItemId,
            Long targetQuoteId) {

        ProductMatchProjection match =
                productEmbeddingRepository.findMostSimilarProduct(
                        sourceItemId,
                        targetQuoteId
                );

        if (match == null) {
            return null;
        }

        if (match.getCosineDistance() > MATCH_THRESHOLD) {
            return null;
        }

        return match;
    }
}