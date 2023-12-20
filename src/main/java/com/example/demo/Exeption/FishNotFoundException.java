package com.example.demo.Exeption;

public class FishNotFoundException extends  RuntimeException{
    public FishNotFoundException(String message) {
        super(message);
    }
}
