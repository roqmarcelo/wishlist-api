package com.luizalabs.domain.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    private CustomerService customerService;

    @BeforeEach
    void init() {
        customerService = new CustomerService(customerDAO);
    }
}