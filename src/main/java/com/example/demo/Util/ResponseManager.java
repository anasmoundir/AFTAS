package com.example.demo.Util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseManager {
    public static ResponseEntity<String> badRequest(String message) {
        return ResponseEntity.badRequest().body(message);
    }
    public static boolean bbadRequest(String message) {
        System.out.println("Bad Request: " + message);
        return false;
    }
    public static ResponseEntity<String> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    public static ResponseEntity<String> noContent() {
        return ResponseEntity.noContent().build();
    }

    public static ResponseEntity<String> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    public static ResponseEntity<String> good(String message) {
        return ResponseEntity.status(HttpStatus.FOUND).body(message);
    }
}