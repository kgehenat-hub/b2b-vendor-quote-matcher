package com.gehena.vendor_quote_matcher.Controller;

import com.gehena.vendor_quote_matcher.kafka.QuoteKafkaProducer;
import com.gehena.vendor_quote_matcher.Repository.VendorRepository;
import com.gehena.vendor_quote_matcher.Service.QuoteService;
import com.gehena.vendor_quote_matcher.entity.Quote;
import com.gehena.vendor_quote_matcher.entity.QuoteStatus;
import com.gehena.vendor_quote_matcher.entity.Vendor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;
    private final VendorRepository vendorRepository;
    private final QuoteKafkaProducer quoteKafkaProducer;

    public QuoteController(
            QuoteService quoteService,
            VendorRepository vendorRepository,
            QuoteKafkaProducer quoteKafkaProducer) {

        this.quoteService = quoteService;
        this.vendorRepository = vendorRepository;
        this.quoteKafkaProducer = quoteKafkaProducer;
    }

    @PostMapping
    public Quote createQuote(@RequestBody Quote quote) {
        return quoteService.createQuote(quote);
    }

    @GetMapping
    public List<Quote> getAllQuote() {
        return quoteService.getAllQuote();
    }

    @PostMapping("/upload")
    public Quote uploadQuote(
            @RequestParam("file") MultipartFile file,
            @RequestParam("vendorId") Long vendorId) throws IOException {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(file.getOriginalFilename());

        Files.write(filePath, file.getBytes());

        Quote quote = new Quote();

        quote.setVendor(vendor);
        quote.setFileName(file.getOriginalFilename());
        quote.setFileType(file.getContentType());
        quote.setStatus(QuoteStatus.UPLOADED);

        Quote savedQuote = quoteService.createQuote(quote);

        quoteKafkaProducer.sendQuoteUploadedEvent(savedQuote.getId());

        return savedQuote;
    }
}