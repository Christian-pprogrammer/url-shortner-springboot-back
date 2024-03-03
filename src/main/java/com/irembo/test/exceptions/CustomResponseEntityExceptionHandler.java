package com.irembo.test.exceptions;

import com.irembo.test.dto.ExceptionPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionPayload> handleUserNotFound(UsernameNotFoundException exception, WebRequest request) {
        ExceptionPayload payload =
                new ExceptionPayload("incorrect username or password", HttpStatus.UNAUTHORIZED);
        return
                new ResponseEntity<>(payload, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ExceptionPayload> handleInvalidUrl(InvalidUrlException exception, WebRequest request) {
        ExceptionPayload payload =
                new ExceptionPayload(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return
                new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionPayload> handleUnAuthorized(UnAuthorizedException exception, WebRequest request) {
        ExceptionPayload payload =
                new ExceptionPayload(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return
                new ResponseEntity<ExceptionPayload>(payload, HttpStatus.UNAUTHORIZED);
    }

}
