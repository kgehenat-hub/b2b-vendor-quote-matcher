package com.gehena.vendor_quote_matcher.Service;

import com.gehena.vendor_quote_matcher.Repository.QuoteRepository;
import com.gehena.vendor_quote_matcher.Repository.VendorRepository;
import com.gehena.vendor_quote_matcher.entity.Quote;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public Quote createQuote(Quote quote){
        return  quoteRepository.save(quote);
    }
    public List<Quote> getAllQuote(){
        return quoteRepository.findAll();
    }
}
