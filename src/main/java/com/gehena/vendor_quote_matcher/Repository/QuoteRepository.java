package com.gehena.vendor_quote_matcher.Repository;

import com.gehena.vendor_quote_matcher.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote , Long> {

}
