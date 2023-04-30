package com.cbshop.demo.exceptions.controlleradvice;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}