package domain.client;

import infrastructure.exception.ClientNotFoundException;
import infrastructure.exception.EmailAlreadyRegisteredException;

import java.util.Objects;

class ClientService {

    private final ClientDAO clientDAO;

    ClientService(final ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    Client find(final Long id) {
        final Client client = clientDAO.find(id);
        if (client == null) {
            throw new ClientNotFoundException();
        }
        return client;
    }

    Long saveOrUpdate(final Client client) {
        if (client.getId() == null) {
            return save(client);
        }
        return update(client);
    }

    boolean delete(final Long id) {
        return clientDAO.delete(id);
    }

    private Long save(final Client client) {
        if (clientDAO.existsBy(client.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }
        return clientDAO.save(client);
    }

    private Long update(final Client client) {
        final Client existingClient = find(client.getId());
        if (clientDAO.existsBy(client.getEmail()) && !Objects.equals(client.getEmail(), existingClient.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }
        if (!clientDAO.update(client)) {
            throw new ClientNotFoundException();
        }
        return client.getId();
    }
}