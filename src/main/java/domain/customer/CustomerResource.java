package domain.customer;

import infrastructure.JsonCompliantHttpServlet;
import infrastructure.exception.CustomerNotFoundException;
import infrastructure.exception.EmailAlreadyRegisteredException;
import org.apache.commons.validator.routines.EmailValidator;
import util.RestUtil;
import util.Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/customers/*")
public class CustomerResource extends JsonCompliantHttpServlet {

    private final CustomerService customerService = new CustomerService(new CustomerDAO());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Long id = RestUtil.extractIdFromClientsPath(request.getPathInfo());
        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            final Customer customer = customerService.find(id);
            sendResponseAsJson(response, customer);
        } catch (CustomerNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPostOrPut(request, response, null);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Long id = RestUtil.extractIdFromClientsPath(request.getPathInfo());
        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        doPostOrPut(request, response, id);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        final Long id = RestUtil.extractIdFromClientsPath(request.getPathInfo());
        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (customerService.delete(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void doPostOrPut(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException {
        final CustomerRequest customer = getRequestAsJson(request, CustomerRequest.class);
        if (!isCustomerValid(customer)) {
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
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getOutputStream().print(e.getMessage());
        } catch (CustomerNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isCustomerValid(final CustomerRequest customer) {
        final int maxLength = 255;
        if (customer == null) {
            return false;
        }
        if (Util.isNullOrEmpty(customer.getName()) || Util.exceedsMaxLength(customer.getName(), maxLength)) {
            return false;
        }
        if (Util.isNullOrEmpty(customer.getEmail()) || Util.exceedsMaxLength(customer.getEmail(), maxLength)) {
            return false;
        }
        return EmailValidator.getInstance().isValid(customer.getEmail());
    }
}