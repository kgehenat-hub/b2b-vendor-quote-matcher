package com.gehena.vendor_quote_matcher.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "product_embeddings")
public class ProductEmbedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "quote_item_id", nullable = false)
    private QuoteItem quoteItem;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Column(columnDefinition = "vector(768)")
    private float[] embedding;

    public Long getId() {
        return id;
    }

    public QuoteItem getQuoteItem() {
        return quoteItem;
    }

    public void setQuoteItem(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }
}