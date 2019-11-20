package domain.customer;

import com.google.gson.JsonObject;
import infrastructure.exception.EmailAlreadyRegisteredException;
import infrastructure.exception.NotFoundException;
import rest.AbstractResourceHandler;
import rest.RestRequestResolver.RestRequestResult;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CustomerResourceHandler extends AbstractResourceHandler {

    private final CustomerService customerService;

    @Inject
    public CustomerResourceHandler(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void handleGet(final RestRequestResult result, final HttpServletResponse response) {
        Objects.requireNonNull(result, "RestRequestResult result cannot be null.");
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        try {
            final Customer customer = customerService.find(result.getCustomerId());
            sendResponseAsJson(response, customer, HttpServletResponse.SC_OK);
        } catch (NotFoundException | IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void handlePost(final RestRequestResult result, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Objects.requireNonNull(request, "HttpServletRequest request cannot be null.");
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        handlePostOrPut(request, response, null);
    }

    @Override
    public void handlePut(final RestRequestResult result, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Objects.requireNonNull(result, "RestRequestResult result cannot be null.");
        Objects.requireNonNull(request, "HttpServletRequest request cannot be null.");
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        handlePostOrPut(request, response, result.getCustomerId());
    }

    @Override
    public void handleDelete(final RestRequestResult result, final HttpServletResponse response) {
        Objects.requireNonNull(result, "RestRequestResult result cannot be null.");
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");

        if (customerService.delete(result.getCustomerId())) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handlePostOrPut(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException {
        final CustomerRequest customer = getRequestAsJson(request, CustomerRequest.class);
        if (customer == null || !customer.isValid()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            final boolean postRequest = (id == null);
            id = customerService.saveOrUpdate(new Customer(id, customer.getName(), customer.getEmail()));
            if (postRequest) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setHeader("Location", String.format("/customers/%d", id));
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (EmailAlreadyRegisteredException e) {
            JsonObject error = new JsonObject();
            error.addProperty("error", e.getMessage());
            sendResponseAsJson(response, error, HttpServletResponse.SC_CONFLICT);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}