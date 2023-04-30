package com.cbshop.demo.exceptions.controlleradvice;

public class ServerError extends RuntimeException {

    public ServerError(String message) {
        super(message);
    }
}