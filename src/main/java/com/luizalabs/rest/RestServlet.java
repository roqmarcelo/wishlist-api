package rest;

import domain.customer.CustomerResourceHandler;
import domain.product.ProductResourceHandler;
import rest.RestRequestResolver.RestRequestResult;
import rest.RestRequestResolver.RestRequestResult.RestRequestType;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class RestServlet extends HttpServlet {

    private final CustomerResourceHandler customerResourceHandler;
    private final ProductResourceHandler productResourceHandler;

    @Inject
    public RestServlet(final CustomerResourceHandler customerResourceHandler, final ProductResourceHandler productResourceHandler) {
        this.customerResourceHandler = customerResourceHandler;
        this.productResourceHandler = productResourceHandler;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        final RestRequestResult result = RestRequestResolver.resolve(request.getPathInfo());
        if (result.getType() == RestRequestType.NONE) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (result.getType() == RestRequestType.CUSTOMER) {
            if (result.getCustomerId() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            customerResourceHandler.handleGet(result, response);
            return;
        }
        productResourceHandler.handleGet(result, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final RestRequestResult result = RestRequestResolver.resolve(request.getPathInfo());
        if (result.getType() == RestRequestType.NONE) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (result.getType() == RestRequestType.CUSTOMER) {
            customerResourceHandler.handlePost(result, request, response);
            return;
        }
        productResourceHandler.handlePost(result, request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final RestRequestResult result = RestRequestResolver.resolve(request.getPathInfo());
        if (result.getType() == RestRequestType.NONE) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (result.getType() == RestRequestType.PRODUCT) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        customerResourceHandler.handlePut(result, request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        final RestRequestResult result = RestRequestResolver.resolve(request.getPathInfo());
        if (result.getType() == RestRequestType.NONE) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (result.getCustomerId() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (result.getType() == RestRequestType.CUSTOMER) {
            customerResourceHandler.handleDelete(result, response);
            return;
        }
        productResourceHandler.handleDelete(result, response);
    }
}