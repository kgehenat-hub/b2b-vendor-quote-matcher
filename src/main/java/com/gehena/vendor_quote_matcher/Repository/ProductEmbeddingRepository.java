package com.gehena.vendor_quote_matcher.Repository;

import com.gehena.vendor_quote_matcher.dto.ProductMatchProjection;
import com.gehena.vendor_quote_matcher.entity.ProductEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductEmbeddingRepository
        extends JpaRepository<ProductEmbedding, Long> {

    @Query(value = """
        SELECT
            pe.quote_item_id AS matchedItemId,
            qi.product AS matchedProduct,
            qi.unit_price AS matchedUnitPrice,
            pe.embedding <=> (
                SELECT embedding
                FROM product_embeddings
                WHERE quote_item_id = :sourceItemId
            ) AS cosineDistance
        FROM product_embeddings pe
        JOIN quote_items qi
            ON qi.id = pe.quote_item_id
        WHERE pe.quote_item_id <> :sourceItemId
          AND qi.quote_id = :targetQuoteId
        ORDER BY cosineDistance
        LIMIT 1
        """, nativeQuery = true)
    ProductMatchProjection findMostSimilarProduct(
            @Param("sourceItemId") Long sourceItemId,
            @Param("targetQuoteId") Long targetQuoteId);
}