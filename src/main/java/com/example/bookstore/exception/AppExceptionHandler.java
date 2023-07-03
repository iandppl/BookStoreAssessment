package com.example.bookstore.exception;

import com.example.bookstore.domain.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BookCreationException.class})
    public ResponseEntity<Object> handleBookCreationException(BookCreationException ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if(errorMessageDescription == null) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(value = BookDeletionErrorException.class)
    public ResponseEntity<Object> handleBookDeletionErrorException(BookDeletionErrorException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if(errorMessageDescription == null) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(value = NoBooksFoundException.class)
    public ResponseEntity<Object> handleNoBooksFoundException(NoBooksFoundException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if(errorMessageDescription == null) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
