package com.cbshop.demo.exceptions.controlleradvice;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}

