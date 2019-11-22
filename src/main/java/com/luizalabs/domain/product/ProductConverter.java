package com.luizalabs.domain.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class ProductConverter {

    private final ResultSet resultSet;

    private ProductConverter(final ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    static ProductConverter ofResultSet(final ResultSet resultSet) {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null.");
        return new ProductConverter(resultSet);
    }

    Product asSingleProduct() throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        return toProduct();
    }

    List<Product> asProductList() throws SQLException {
        final List<Product> customerProducts = new ArrayList<>();
        while (resultSet.next()) {
            customerProducts.add(toProduct());
        }
        return customerProducts;
    }

    private Product toProduct() throws SQLException {
        final String productId = resultSet.getString("productId");
        final String title = resultSet.getString("title");
        final String image = resultSet.getString("image");
        final Double price = resultSet.getDouble("price");
        final Integer reviewScore = resultSet.getInt("reviewScore");
        return new Product(productId, title, image, price, reviewScore);
    }
}