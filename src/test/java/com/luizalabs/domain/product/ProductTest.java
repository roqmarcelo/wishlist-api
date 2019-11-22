package com.luizalabs.domain.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTest {

    @Test
    void createProductImmutableData() {
        Product product = new Product("productId", "title", "image",6000.0d, 10);
        assertEquals(product.getProductId(), "productId");
        assertEquals(product.getTitle(), "title");
        assertEquals(product.getImage(), "image");
        assertEquals(product.getPrice(), 6000.0d);
        assertEquals(product.getReviewScore(), 10);
        assertNull(product.getId());
        assertNull(product.getCustomerId());
    }

    @Test
    void createProductSetCustomerId() {
        Product product = new Product("productId", "title", "image",6000.0d, 10);
        product.setCustomerId(10L);
        assertEquals(product.getCustomerId(), 10L);
    }
}