package com.gehena.vendor_quote_matcher.Controller;

import com.gehena.vendor_quote_matcher.Service.EmbeddingService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/test")
    public String testEmbedding(@RequestParam String text) {

        float[] embedding = embeddingService.generateEmbedding(text);

        return "Vector size: " + embedding.length +
                "\nFirst 5 values: " +
                Arrays.toString(Arrays.copyOf(embedding, 5));
    }
}
