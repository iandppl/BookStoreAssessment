package com.example.bookstore.exception;

public class BookCreationErrorException extends RuntimeException{
    public BookCreationErrorException(String message) {
        super(message);
    }
}
