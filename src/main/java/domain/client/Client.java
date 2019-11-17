package domain.client;

import java.util.Objects;

class Client {
    private Long id;
    private String name;
    private String email;

    private Client() {}

    Client(final Long id, final String name, final String email) {
        this.id = Objects.requireNonNull(id, "Id cannot be null.");
        this.name = Objects.requireNonNull(name, "Name cannot be null.");
        this.email = Objects.requireNonNull(email, "E-mail cannot be null.");
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }
}