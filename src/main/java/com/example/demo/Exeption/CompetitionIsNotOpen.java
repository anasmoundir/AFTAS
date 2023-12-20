package com.example.demo.Exeption;

public class CompetitionIsNotOpen extends RuntimeException{
    public CompetitionIsNotOpen(String message) {super(message);}
}
