package com.gehena.vendor_quote_matcher.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class QuoteKafkaProducer {

    private static final String TOPIC = "quote-uploaded";

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public QuoteKafkaProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendQuoteUploadedEvent(Long quoteId) {
        kafkaTemplate.send(TOPIC, quoteId);
    }
}