package com.luizalabs.domain.product;

import com.luizalabs.infrastructure.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductRestClientTest {

    @Test
    void getProductFromApiThrowsNotFoundException() {
        assertThrows(NotFoundException.class,
                () -> ProductRestClient.get("206d2670-fe9e-48fc-b650-71ac81b9371c"),
                "Product information not found.");
    }

    @Test
    void getProductFromApiSuccess() {
        assertNotNull(ProductRestClient.get("1bf0f365-fbdd-4e21-9786-da459d78dd1f"));
    }
}