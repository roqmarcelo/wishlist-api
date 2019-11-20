package domain.product;

import infrastructure.exception.NotFoundException;
import infrastructure.exception.ProductAlreadyAddedException;

import javax.inject.Inject;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    @Inject
    public ProductService(final ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    List<Product> list(final Long customerId) {
        return productDAO.list(customerId);
    }

    Product find(final Long customerId, final String productId) {
        final Product product = productDAO.find(customerId, productId);
        if (product == null) {
            throw new NotFoundException("Product not found.");
        }
        return product;
    }

    void save(final Long customerId, final String productId) {
        if (productDAO.exists(customerId, productId)) {
            throw new ProductAlreadyAddedException();
        }
        final ProductResponse productResponse = ProductRestClient.get(productId);
        final Product product = productResponse.asProduct();
        product.setCustomerId(customerId);
        productDAO.save(product);
    }

    boolean delete(final Long customerId, final String productId) {
        return productDAO.delete(customerId, productId);
    }
}