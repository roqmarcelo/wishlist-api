package com.luizalabs.domain.customer;

import java.util.Objects;

class Customer {
    private Long id;
    private String name;
    private String email;

    Customer(final Long id, final String name, final String email) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name cannot be null.");
        this.email = Objects.requireNonNull(email, "E-mail cannot be null.");
    }

    Long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }
}