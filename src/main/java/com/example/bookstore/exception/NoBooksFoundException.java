package com.example.bookstore.exception;

public class NoBooksFoundException extends RuntimeException{
    public NoBooksFoundException(String message) {
        super(message);
    }
}
