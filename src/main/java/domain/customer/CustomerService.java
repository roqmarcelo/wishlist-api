package domain.customer;

import infrastructure.exception.EmailAlreadyRegisteredException;
import infrastructure.exception.NotFoundException;

import javax.inject.Inject;
import java.util.Objects;

public class CustomerService {

    private final CustomerDAO customerDAO;

    @Inject
    public CustomerService(final CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    Customer find(final Long id) {
        final Customer customer = customerDAO.find(id);
        if (customer == null) {
            throw new NotFoundException("Customer not found.");
        }
        return customer;
    }

    Long saveOrUpdate(final Customer customer) {
        if (customer.getId() == null) {
            return save(customer);
        }
        return update(customer);
    }

    boolean delete(final Long id) {
        return customerDAO.delete(id);
    }

    private Long save(final Customer customer) {
        if (customerDAO.existsBy(customer.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }
        return customerDAO.save(customer);
    }

    private Long update(final Customer customer) {
        final Customer existingCustomer = find(customer.getId());
        if (customerDAO.existsBy(customer.getEmail()) && !Objects.equals(customer.getEmail(), existingCustomer.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }
        if (!customerDAO.update(customer)) {
            throw new NotFoundException("Customer not found.");
        }
        return customer.getId();
    }
}