package com.example.demo.Exeption;

public class PersonValidationException  extends RuntimeException {
    public PersonValidationException(String message) {
        super(message);
    }
}
