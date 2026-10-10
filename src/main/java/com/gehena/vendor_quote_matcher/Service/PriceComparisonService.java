package com.gehena.vendor_quote_matcher.Service;

import com.gehena.vendor_quote_matcher.Repository.QuoteItemRepository;
import com.gehena.vendor_quote_matcher.dto.PriceComparisonResponse;
import com.gehena.vendor_quote_matcher.dto.ProductMatchProjection;
import com.gehena.vendor_quote_matcher.dto.QuoteComparisonResponse;
import com.gehena.vendor_quote_matcher.entity.QuoteItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceComparisonService {

    private final QuoteItemRepository quoteItemRepository;
    private final ProductMatchingService productMatchingService;

    public PriceComparisonService(
            QuoteItemRepository quoteItemRepository,
            ProductMatchingService productMatchingService) {

        this.quoteItemRepository = quoteItemRepository;
        this.productMatchingService = productMatchingService;
    }

    public QuoteComparisonResponse compareQuotes(
            Long sourceQuoteId,
            Long targetQuoteId) {

        // Get all items from both quotes
        List<QuoteItem> sourceItems =
                quoteItemRepository.findByQuoteId(sourceQuoteId);

        List<QuoteItem> targetItems =
                quoteItemRepository.findByQuoteId(targetQuoteId);

        List<PriceComparisonResponse> results =
                new ArrayList<>();

        // Calculate total cost of source quote
        double sourceQuoteTotal = 0;

        for (QuoteItem item : sourceItems) {

            double itemTotal =
                    item.getQuantity() * item.getUnitPrice();

            sourceQuoteTotal += itemTotal;
        }

        // Calculate total cost of target quote
        double targetQuoteTotal = 0;

        for (QuoteItem item : targetItems) {

            double itemTotal =
                    item.getQuantity() * item.getUnitPrice();

            targetQuoteTotal += itemTotal;
        }

        // Compare every source item with target quote
        for (QuoteItem sourceItem : sourceItems) {

            ProductMatchProjection match =
                    productMatchingService.findBestMatch(
                            sourceItem.getId(),
                            targetQuoteId
                    );

            if (match == null) {
                continue;
            }

            double sourcePrice =
                    sourceItem.getUnitPrice();

            double matchedPrice =
                    match.getMatchedUnitPrice();

            double priceDifference =
                    Math.abs(sourcePrice - matchedPrice);

            String cheaperOption;

            if (sourcePrice < matchedPrice) {
                cheaperOption = "SOURCE";
            } else if (matchedPrice < sourcePrice) {
                cheaperOption = "MATCHED";
            } else {
                cheaperOption = "SAME_PRICE";
            }

            PriceComparisonResponse response =
                    new PriceComparisonResponse(
                            sourceItem.getId(),
                            sourceItem.getProduct(),
                            sourcePrice,
                            match.getMatchedItemId(),
                            match.getMatchedProduct(),
                            matchedPrice,
                            priceDifference,
                            cheaperOption
                    );

            results.add(response);
        }

        // Calculate overall savings
        double potentialSavings =
                Math.abs(sourceQuoteTotal - targetQuoteTotal);

        String cheaperQuote;

        if (sourceQuoteTotal < targetQuoteTotal) {
            cheaperQuote = "SOURCE";
        } else if (targetQuoteTotal < sourceQuoteTotal) {
            cheaperQuote = "TARGET";
        } else {
            cheaperQuote = "SAME_PRICE";
        }

        return new QuoteComparisonResponse(
                sourceQuoteTotal,
                targetQuoteTotal,
                potentialSavings,
                cheaperQuote,
                results
        );
    }
}