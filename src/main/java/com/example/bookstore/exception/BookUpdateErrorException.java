package com.example.bookstore.exception;

public class BookUpdateErrorException extends RuntimeException{
    public BookUpdateErrorException(String message) {
        super(message);
    }
}
