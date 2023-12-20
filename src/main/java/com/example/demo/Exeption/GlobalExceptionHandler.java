package org.springframework.web;

import com.example.demo.Exeption.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFoundException(final PersonNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonValidationException.class)
    public ResponseEntity<String> handlePersonValidationException(final PersonValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CompetitionNotFoundException.class)
    public ResponseEntity<String> handleCompetitionNotFoundException(final CompetitionNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MemberExistedInTheCompetition.class)
    public  ResponseEntity<String> handleMemberExistInCompetitionException(final MemberExistedInTheCompetition e)
    {
        return  new ResponseEntity<>(e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MemberIsNotRegistredInCompetitionException.class)
    public  ResponseEntity<String> handleNotRegistredInCompetitionException(final  MemberIsNotRegistredInCompetitionException e)
    {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FishNotFoundException.class)
    public  ResponseEntity<String > handleFishNotFoundException(final  FishNotFoundException e)
    {
        return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

}
