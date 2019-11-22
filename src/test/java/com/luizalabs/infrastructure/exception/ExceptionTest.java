package com.luizalabs.infrastructure.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionTest {

    @Test
    void emailAlreadyRegisteredException() {
        Assertions.assertThrows(EmailAlreadyRegisteredException.class,
                () -> { throw new EmailAlreadyRegisteredException(); },
                "E-mail already registered.");
    }

    @Test
    void notFoundException() {
        Assertions.assertThrows(NotFoundException.class,
                () -> { throw new NotFoundException("Not found."); },
                "Not found.");
    }

    @Test
    void productAlreadyAddedException() {
        Assertions.assertThrows(ProductAlreadyAddedException.class,
                () -> { throw new ProductAlreadyAddedException(); },
                "Product already added.");
    }
}