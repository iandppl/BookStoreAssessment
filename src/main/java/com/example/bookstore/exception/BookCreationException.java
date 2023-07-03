package com.example.bookstore.exception;

public class BookCreationException extends RuntimeException{
    public BookCreationException(String message) {
        super(message);
    }
}
