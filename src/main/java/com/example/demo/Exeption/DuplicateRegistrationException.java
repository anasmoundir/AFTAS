package com.example.demo.Exeption;

public class DuplicateRegistrationException extends  RuntimeException{
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
