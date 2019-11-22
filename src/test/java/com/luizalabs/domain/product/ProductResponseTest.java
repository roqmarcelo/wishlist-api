package com.luizalabs.domain.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductResponseTest {

    @Test
    void productResponseAsProductEmpty() {
        Product product = new ProductResponse().asProduct();
        assertNull(product.getId());
        assertNull(product.getTitle());
        assertNull(product.getImage());
        assertNull(product.getPrice());
        assertNull(product.getReviewScore());
    }

    @Test
    void createProductResponseImmutableData() {
        ProductResponse productResponse = new ProductResponse("productId", "title", "image",6000.0d, 10);
        assertEquals(productResponse.getId(), "productId");
        assertEquals(productResponse.getTitle(), "title");
        assertEquals(productResponse.getImage(), "image");
        assertEquals(productResponse.getPrice(), 6000.0d);
        assertEquals(productResponse.getReviewScore(), 10);
    }

    @Test
    void createProductResponseConvertedImmutableData() {
        Product product = new ProductResponse("productId", "title", "image",6000.0d, 10).asProduct();
        assertEquals(product.getProductId(), "productId");
        assertEquals(product.getTitle(), "title");
        assertEquals(product.getImage(), "image");
        assertEquals(product.getPrice(), 6000.0d);
        assertEquals(product.getReviewScore(), 10);
        assertNull(product.getId());
    }
}