package com.luizalabs.domain.product;

import com.luizalabs.infrastructure.exception.NotFoundException;
import com.luizalabs.infrastructure.exception.ProductAlreadyAddedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    private ProductService productService;

    @BeforeEach
    void init() {
        productService = new ProductService(productDAO);
    }

    @Test
    void listEmpty() {
        assertTrue(productService.list(1L).isEmpty());
    }

    @Test
    void listSuccess() {
        Product product = new Product("1bf0f365-fbdd-4e21-9786-da459d78dd1f", "title", "image", 6000.0d, 10);
        List<Product> products = new ArrayList<>(1);
        products.add(product);
        when(productDAO.list(1L)).thenReturn(products);
        assertEquals(productService.list(1L).size(), 1);
    }

    @Test
    void findNotFoundException() {
        assertThrows(NotFoundException.class, () -> productService.find(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f"),
                "Product not found.");
    }

    @Test
    void findSuccess() {
        Product product = new Product("1bf0f365-fbdd-4e21-9786-da459d78dd1f", "title", "image", 6000.0d, 10);
        when(productDAO.find(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f")).thenReturn(product);
        assertNotNull(productService.find(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f"));
    }

    @Test
    void saveProductAlreadyAddedException() {
        when(productDAO.exists(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f")).thenReturn(true);
        assertThrows(ProductAlreadyAddedException.class, () -> productService.save(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f"),
                "Product already added.");
    }

    @Test
    void saveSuccess() {
        productService.save(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f");
    }

    @Test
    void deleteSuccess() {
        when(productDAO.delete(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f")).thenReturn(true);
        assertTrue(productService.delete(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f"));
    }

    @Test
    void deleteFail() {
        when(productDAO.delete(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f")).thenReturn(false);
        assertFalse(productService.delete(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f"));
    }
}