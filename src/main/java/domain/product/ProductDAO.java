package domain.product;

import infrastructure.database.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDAO {

    private static final String SELECT_CUSTOMER_PRODUCTS_SQL = "SELECT productId, title, image, price, reviewScore FROM product WHERE customerId = ?";
    private static final String SELECT_PRODUCT_SQL = "SELECT productId, title, image, price, reviewScore FROM product WHERE customerId = ? and productId = ?";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product(customerId, productId, title, image, price, reviewScore) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE customerId = ? and productId = ?";
    private static final String EXISTS_PRODUCT_SQL = "SELECT count(id) FROM product WHERE customerId = ? and productId = ?";

    List<Product> list(final Long customerId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CUSTOMER_PRODUCTS_SQL)) {
            statement.setLong(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return ProductConverter.ofResultSet(resultSet).asProductList();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Product find(final Long customerId, final String productId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_SQL)) {
            statement.setLong(1, customerId);
            statement.setString(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return ProductConverter.ofResultSet(resultSet).asSingleProduct();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void save(final Product product) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            statement.setLong(1, product.getCustomerId());
            statement.setString(2, product.getProductId());
            statement.setString(3, product.getTitle());
            statement.setString(4, product.getImage());
            statement.setDouble(5, product.getPrice());
            statement.setInt(6, product.getReviewScore());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Could not create client.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean delete(final Long customerId, final String productId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setLong(1, customerId);
            statement.setString(2, productId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean exists(final Long customerId, final String productId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_PRODUCT_SQL)) {
            statement.setLong(1, customerId);
            statement.setString(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}