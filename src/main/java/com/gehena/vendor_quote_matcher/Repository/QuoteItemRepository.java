package com.gehena.vendor_quote_matcher.Repository;

import com.gehena.vendor_quote_matcher.entity.QuoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteItemRepository
        extends JpaRepository<QuoteItem, Long> {

    List<QuoteItem> findByQuoteId(Long quoteId);
}