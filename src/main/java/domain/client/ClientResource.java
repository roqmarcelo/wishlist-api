package domain.client;

import infrastructure.JsonCompliantHttpServlet;
import infrastructure.exception.ClientNotFoundException;
import infrastructure.exception.EmailAlreadyRegisteredException;
import org.apache.commons.validator.routines.EmailValidator;
import util.RestUtil;
import util.Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/clients/*")
public class ClientResource extends JsonCompliantHttpServlet<Client> {

    private final ClientService clientService = new ClientService(new ClientDAO());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Long id = RestUtil.extractIdFromClientsPath(request.getPathInfo());
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            final Client client = clientService.find(id);
            sendResponseAsJson(response, client);
        } catch (ClientNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        doPostOrPut(request, response, id);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Long id = RestUtil.extractIdFromClientsPath(request.getPathInfo());
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (clientService.delete(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void doPostOrPut(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException {
        final Client client = getRequestAsJson(request);
        if (!isClientValid(client)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        client.setId(id);
        try {
            final boolean postRequest = (id == null);
            id = clientService.saveOrUpdate(client);
            if (postRequest) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setHeader("Location", String.format("/clients/%d", id));
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (EmailAlreadyRegisteredException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (ClientNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isClientValid(final Client client) {
        final int maxLength = 255;
        if (client == null) {
            return false;
        }
        if (Util.isNullOrEmpty(client.getName()) || Util.exceedsMaxLength(client.getName(), maxLength)) {
            return false;
        }
        return !Util.exceedsMaxLength(client.getEmail(), maxLength) && EmailValidator.getInstance().isValid(client.getEmail());
    }
}