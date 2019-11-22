package com.luizalabs.domain.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRequestTest {

    private static final String EXCEEDING = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis,.";

    @Test
    void createCustomerRequestNameInvalid() {
        CustomerRequest customerRequest = new CustomerRequest(null, "valid@email.com");
        assertFalse(customerRequest.isValid());
    }

    @Test
    void createCustomerRequestNameExceedsMaxLength() {
        CustomerRequest customerRequest = new CustomerRequest(EXCEEDING, "valid@email.com");
        assertFalse(customerRequest.isValid());
    }

    @Test
    void createCustomerRequestEmailInvalid() {
        CustomerRequest customerRequest = new CustomerRequest("Customer", "invalid@.");
        assertFalse(customerRequest.isValid());
    }

    @Test
    void createCustomerRequestEmailNull() {
        CustomerRequest customerRequest = new CustomerRequest("Customer", null);
        assertFalse(customerRequest.isValid());
    }

    @Test
    void createCustomerRequestEmailExceedsMaxLength() {
        CustomerRequest customerRequest = new CustomerRequest("Customer", EXCEEDING);
        assertFalse(customerRequest.isValid());
    }

    @Test
    void createCustomerRequestValid() {
        CustomerRequest customerRequest = new CustomerRequest("Customer", "valid@email.com");
        assertTrue(customerRequest.isValid());
    }

    @Test
    void createCustomerRequestSuccessImmutableData() {
        CustomerRequest customerRequest = new CustomerRequest("Customer", "valid@email.com");
        assertEquals(customerRequest.getName(), "Customer");
        assertEquals(customerRequest.getEmail(), "valid@email.com");
    }
}