package domain.customer;

import infrastructure.exception.CustomerNotFoundException;
import infrastructure.exception.EmailAlreadyRegisteredException;

import java.util.Objects;

class CustomerService {

    private final CustomerDAO customerDAO;

    CustomerService(final CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    Customer find(final Long id) {
        final Customer customer = customerDAO.find(id);
        if (customer == null) {
            throw new CustomerNotFoundException();
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
            throw new CustomerNotFoundException();
        }
        return customer.getId();
    }
}