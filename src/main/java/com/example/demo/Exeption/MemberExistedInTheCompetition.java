package com.example.demo.Exeption;

public class MemberExistedInTheCompetition extends RuntimeException{
    public MemberExistedInTheCompetition() {
        super("the member is already registred");
    }
}
