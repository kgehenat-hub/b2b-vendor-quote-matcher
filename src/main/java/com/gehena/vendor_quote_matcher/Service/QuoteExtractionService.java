package com.gehena.vendor_quote_matcher.Service;

import com.gehena.vendor_quote_matcher.dto.QuoteExtractionResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class QuoteExtractionService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public QuoteExtractionService(ChatClient.Builder chatClientBuilder , ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public  QuoteExtractionResponse extractQuoteData(String extractedText) {

        System.out.println("Sending request to Ollama...");

        String response = chatClient
                .prompt()
                .user("""
                        Extract the vendor quote information from the following text.

                        Return ONLY valid JSON in this format:
                          Follow these rules:
                                1. Do not use commas inside numeric values.
                                2. Numbers must be plain numbers.
                                3. For example, write 52000, NOT 52,000.
                                4. For example, write 520000, NOT 5,20,000.
                                5. Do not add markdown.
                                6. Do not add explanations.
                                 Return this exact structure:
                        

                        {
                          "vendorName": "",
                          "email": "",
                          "companyName": "",
                          "items": [
                            {
                              "product": "",
                              "description": "",
                              "quantity": 0,
                              "unitPrice": 0,
                              "totalPrice": 0
                            }
                          ]
                        }

                        Do not add markdown.
                        Do not add explanations.

                        Vendor quote text:
                        %s
                        """.formatted(extractedText))
                .call()
                .content();

        System.out.println("Ollama response received!");
        System.out.println(response);

        try {
            return objectMapper.readValue(
                    response,
                    QuoteExtractionResponse.class
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to convert Ollama response to JSON object", e
            );
        }


    }
}