package domain.wishlist;

import java.util.List;

class ProductDAO {

    private static final String SELECT_CUSTOMER_PRODUCTS_SQL = "SELECT productId, title, image, price, reviewScore FROM product WHERE customerId = ?";
    private static final String SELECT_PRODUCT_SQL = "SELECT productId, title, image, price, reviewScore FROM product WHERE customerId = ? and productId = ?";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product(customerId, productId, title, image, price, reviewScore) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE customerId = ? and productId = ?";
    private static final String EXISTS_PRODUCT_SQL = "SELECT count(id) FROM product WHERE customerId = ? and productId = ?";

    List<Product> list(final Long customerId) {
        return null;
    }

    Product find(final String productId) {
        return null;
    }

    Long save(final Product product) {
        return 0L;
    }

    boolean delete(final Long customerId, final String productId) {
        return false;
    }

    boolean exists(final Long customerId, final String productId) {
        return false;
    }
}