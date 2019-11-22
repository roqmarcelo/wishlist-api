package com.luizalabs.infrastructure.exception;

public class ProductAlreadyAddedException extends RuntimeException {
    public ProductAlreadyAddedException() {
        super("Product already added.");
    }
}