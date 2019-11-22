package com.luizalabs.domain.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {

    @Test
    void createInvalidCustomerThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Customer(null, null, null));
    }

    @Test
    void createInvalidCustomerThrowsNullPointerExceptionForName() {
        assertThrows(NullPointerException.class, () -> new Customer(null, null, "valid@email.com"), "Name cannot be null.");
    }

    @Test
    void createInvalidCustomerThrowsNullPointerExceptionForEmail() {
        assertThrows(NullPointerException.class, () -> new Customer(null, "Customer", null), "E-mail cannot be null.");
    }

    @Test
    void createCustomerSuccess() {
        new Customer(null, "Customer", "valid@email.com");
    }

    @Test
    void createCustomerSuccessImmutableData() {
        Customer customer = new Customer(1L, "Customer", "valid@email.com");
        assertEquals(customer.getId(), 1L);
        assertEquals(customer.getName(), "Customer");
        assertEquals(customer.getEmail(), "valid@email.com");
    }
}