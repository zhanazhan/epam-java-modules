package com.epam.jmp.service.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException() {}

    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
