package org.example.kitchen.exception;

public class FridgeFullException extends RuntimeException {
    public FridgeFullException(String message) {
        super(message);
    }
}
