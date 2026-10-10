package com.gehena.vendor_quote_matcher.kafka;

import com.gehena.vendor_quote_matcher.Repository.QuoteItemRepository;
import com.gehena.vendor_quote_matcher.Repository.QuoteRepository;
import com.gehena.vendor_quote_matcher.Service.ProductEmbeddingService;
import com.gehena.vendor_quote_matcher.Service.QuoteExtractionService;
import com.gehena.vendor_quote_matcher.dto.QuoteExtractionResponse;
import com.gehena.vendor_quote_matcher.entity.Quote;
import com.gehena.vendor_quote_matcher.entity.QuoteItem;
import com.gehena.vendor_quote_matcher.entity.QuoteStatus;
import com.gehena.vendor_quote_matcher.util.PdfTextExtractor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QuoteKafkaConsumer {

    private final QuoteRepository quoteRepository;
    private final QuoteExtractionService quoteExtractionService;
    private final QuoteItemRepository quoteItemRepository;
    private final ProductEmbeddingService productEmbeddingService;
    public QuoteKafkaConsumer(
            QuoteRepository quoteRepository,
            QuoteExtractionService quoteExtractionService, QuoteItemRepository quoteItemRepository, ProductEmbeddingService productEmbeddingService) {

        this.quoteRepository = quoteRepository;
        this.quoteExtractionService = quoteExtractionService;
        this.quoteItemRepository = quoteItemRepository;
        this.productEmbeddingService = productEmbeddingService;
    }

    @KafkaListener(
            topics = "quote-uploaded",
            groupId = "quote-processing-group"
    )
    public void processQuote(Long quoteId) {

        System.out.println("Received Quote ID from Kafka: " + quoteId);

        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        try {

            // 1. Change status
            quote.setStatus(QuoteStatus.PROCESSING);
            quoteRepository.save(quote);

            System.out.println(
                    "Quote " + quoteId + " is now PROCESSING"
            );

            // 2. Find uploaded PDF
            Path filePath = Paths.get(
                    "uploads",
                    quote.getFileName()
            );

            System.out.println("Reading PDF: " + filePath);

            // 3. Extract text using PDFBox
            String extractedText =
                    PdfTextExtractor.extractText(filePath);

            System.out.println("========== EXTRACTED TEXT ==========");
            System.out.println(extractedText);
            System.out.println("====================================");

            // 4. Send extracted text to Ollama
            QuoteExtractionResponse structuredData =
                    quoteExtractionService.extractQuoteData(extractedText);

            structuredData.getItems().forEach(item -> {

                QuoteItem quoteItem = new QuoteItem();

                quoteItem.setQuote(quote);
                quoteItem.setProduct(item.getProduct());
                quoteItem.setDescription(item.getDescription());
                quoteItem.setQuantity(item.getQuantity());
                quoteItem.setUnitPrice(item.getUnitPrice());
                quoteItem.setTotalPrice(item.getTotalPrice());

                QuoteItem savedQuoteItem = quoteItemRepository.save(quoteItem);

                productEmbeddingService.generateAndSaveEmbedding(savedQuoteItem);
            });
            quote.setStatus(QuoteStatus.EXTRACTED);
            quoteRepository.save(quote);

            // 5. Print AI response
            System.out.println("========== AI RESPONSE ==========");
            System.out.println(structuredData);
            System.out.println("================================");

        } catch (Exception e) {

            quote.setStatus(QuoteStatus.FAILED);
            quoteRepository.save(quote);

            System.out.println(
                    "Failed to process Quote " + quoteId
            );

            e.printStackTrace();
        }
    }
}