package com.gehena.vendor_quote_matcher.Service;

import com.gehena.vendor_quote_matcher.Repository.ProductEmbeddingRepository;
import com.gehena.vendor_quote_matcher.entity.ProductEmbedding;
import com.gehena.vendor_quote_matcher.entity.QuoteItem;
import org.springframework.stereotype.Service;

@Service
public class ProductEmbeddingService {

    private final EmbeddingService embeddingService;
    private final ProductEmbeddingRepository productEmbeddingRepository;

    public ProductEmbeddingService(
            EmbeddingService embeddingService,
            ProductEmbeddingRepository productEmbeddingRepository) {

        this.embeddingService = embeddingService;
        this.productEmbeddingRepository = productEmbeddingRepository;
    }

    public void generateAndSaveEmbedding(QuoteItem quoteItem) {

        String text = quoteItem.getProduct()
                + " "
                + quoteItem.getDescription();

        float[] vector = embeddingService.generateEmbedding(text);

        ProductEmbedding productEmbedding = new ProductEmbedding();

        productEmbedding.setQuoteItem(quoteItem);
        productEmbedding.setEmbedding(vector);

        productEmbeddingRepository.save(productEmbedding);

        System.out.println(
                "Embedding saved for QuoteItem ID: "
                        + quoteItem.getId()
        );
    }
}