package com.luizalabs.infrastructure.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException() {
        super("E-mail already registered.");
    }
}