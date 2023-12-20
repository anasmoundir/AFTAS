package com.example.demo.Exeption;

public class PersonNotFoundException extends RuntimeException  {
    public PersonNotFoundException(String message) {
        super(message);
    }
}

