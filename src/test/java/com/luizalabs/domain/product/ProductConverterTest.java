package com.luizalabs.domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductConverterTest {

    @Mock
    private ResultSet resultSet;

    @Test
    void productOfResultSetNull() {
        assertThrows(NullPointerException.class, () -> ProductConverter.ofResultSet(null), "ResultSet cannot be null.");
    }

    @Test
    void productOfResultSetSingleProductEmpty() throws SQLException {
        ProductConverter converter =  ProductConverter.ofResultSet(resultSet);
        assertNull(converter.asSingleProduct());
    }

    @Test
    void productOfResultSetProductListEmpty() throws SQLException {
        ProductConverter converter =  ProductConverter.ofResultSet(resultSet);
        assertTrue(converter.asProductList().isEmpty());
    }

    @Test
    void productOfResultSetSingleProduct() throws SQLException {
        when((resultSet.next())).thenReturn(true);
        productOfResultBindings();
        ProductConverter converter = ProductConverter.ofResultSet(resultSet);
        Product product = converter.asSingleProduct();
        productOfResultAssertions(product);
    }

    @Test
    void productOfResultSetProductList() throws SQLException {
        when((resultSet.next())).thenReturn(true).thenReturn(false);
        productOfResultBindings();
        ProductConverter converter = ProductConverter.ofResultSet(resultSet);
        List<Product> productList = converter.asProductList();
        assertFalse(productList.isEmpty());
        productOfResultAssertions(productList.get(0));
    }

    void productOfResultBindings() throws SQLException {
        when(resultSet.getString("productId")).thenReturn("productId");
        when(resultSet.getString("title")).thenReturn("title");
        when(resultSet.getString("image")).thenReturn("image");
        when(resultSet.getDouble("price")).thenReturn(6000.0d);
        when(resultSet.getInt("reviewScore")).thenReturn(10);
    }

    void productOfResultAssertions(Product product) {
        assertEquals(product.getProductId(), "productId");
        assertEquals(product.getTitle(), "title");
        assertEquals(product.getImage(), "image");
        assertEquals(product.getPrice(), 6000.0d);
        assertEquals(product.getReviewScore(), 10);
    }
}