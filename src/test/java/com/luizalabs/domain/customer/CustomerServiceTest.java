package com.luizalabs.domain.customer;

import com.luizalabs.infrastructure.exception.EmailAlreadyRegisteredException;
import com.luizalabs.infrastructure.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    private CustomerService customerService;

    @BeforeEach
    void init() {
        customerService = new CustomerService(customerDAO);
    }

    @Test
    void saveEmailAlreadyRegisteredException() {
        when(customerDAO.existsBy("valid@email.com")).thenReturn(true);
        Customer customer = new Customer(null, "Customer", "valid@email.com");
        assertThrows(EmailAlreadyRegisteredException.class, () -> customerService.saveOrUpdate(customer),
                "E-mail already registered.");
    }

    @Test
    void updateNotFoundException() {
        when(customerDAO.existsBy("valid@email.com")).thenReturn(true);
        Customer customer = new Customer(1L, "Customer", "valid@email.com");
        lenient().when(customerDAO.find(1L)).thenReturn(customer);
        assertThrows(NotFoundException.class, () -> customerService.saveOrUpdate(customer),
                "Customer not found.");
    }

    @Test
    void updateEmailAlreadyRegisteredException() {
        Customer existingCustomer  = new Customer(1L, "Existing Customer", "another@email.com");
        Customer customer = new Customer(1L, "Customer", "valid@email.com");
        when(customerDAO.find(1L)).thenReturn(existingCustomer);
        when(customerDAO.existsBy("valid@email.com")).thenReturn(true);
        assertThrows(EmailAlreadyRegisteredException.class, () -> customerService.saveOrUpdate(customer),
                "E-mail already registered.");
    }

    @Test
    void saveSuccess() {
        Customer customer = new Customer(null, "Customer", "valid@email.com");
        when(customerDAO.existsBy("valid@email.com")).thenReturn(false);
        when(customerDAO.save(customer)).thenReturn(1L);
        assertEquals(customerService.saveOrUpdate(customer), 1L);
    }

    @Test
    void updateSuccess() {
        Customer customer = new Customer(1L, "Customer", "valid@email.com");
        when(customerDAO.find(1L)).thenReturn(customer);
        when(customerDAO.existsBy("valid@email.com")).thenReturn(false);
        when(customerDAO.update(customer)).thenReturn(true);
        assertEquals(customerService.saveOrUpdate(customer), 1L);
    }

    @Test
    void deleteSuccess() {
        when(customerDAO.delete(1L)).thenReturn(true);
        assertTrue(customerService.delete(1L));
    }

    @Test
    void deleteFail() {
        when(customerDAO.delete(1L)).thenReturn(false);
        assertFalse(customerService.delete(1L));
    }
}