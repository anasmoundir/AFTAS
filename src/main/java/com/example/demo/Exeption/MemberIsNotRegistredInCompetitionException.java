package com.example.demo.Exeption;

public class MemberIsNotRegistredInCompetitionException extends  RuntimeException {
    public MemberIsNotRegistredInCompetitionException(String message)
    {
        super(message);
    }
}
