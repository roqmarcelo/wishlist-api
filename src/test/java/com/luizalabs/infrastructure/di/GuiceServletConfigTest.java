package com.luizalabs.infrastructure.di;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GuiceServletConfigTest {

    @Test
    void getInjector() {
        assertNotNull(new GuiceServletConfig().getInjector());
    }
}