package domain.product;

import com.google.gson.JsonObject;
import infrastructure.exception.NotFoundException;
import infrastructure.exception.ProductAlreadyAddedException;
import rest.AbstractResourceHandler;
import rest.RestRequestResolver.RestRequestResult;
import util.StringUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProductResourceHandler extends AbstractResourceHandler {

    private final ProductService productService;

    @Inject
    public ProductResourceHandler(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void handleGet(final RestRequestResult result, final HttpServletResponse response) {
        Objects.requireNonNull(result, "RestRequestResult result cannot be null.");
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        try {
            if (StringUtils.isNullOrEmpty(result.getProductId())) {
                final List<Product> products = productService.list(result.getCustomerId());
                sendResponseAsJson(response, products, HttpServletResponse.SC_OK);
                return;
            }
            final Product product = productService.find(result.getCustomerId(), result.getProductId());
            sendResponseAsJson(response, product, HttpServletResponse.SC_OK);
        } catch (NotFoundException | IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void handlePost(final RestRequestResult result, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Objects.requireNonNull(response, "HttpServletRequest request cannot be null.");
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        try {
            productService.save(result.getCustomerId(), result.getProductId());

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setHeader("Location", String.format("/customers/%d/wishlist/%s", result.getCustomerId(), result.getProductId()));
        } catch (ProductAlreadyAddedException e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", e.getMessage());
            sendResponseAsJson(response, error, HttpServletResponse.SC_CONFLICT);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void handlePut(final RestRequestResult result, final HttpServletRequest request, final HttpServletResponse response) {
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    public void handleDelete(final RestRequestResult result, final HttpServletResponse response) {
        Objects.requireNonNull(result, "RestRequestResult result cannot be null.");
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        if (productService.delete(result.getCustomerId(), result.getProductId())) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}