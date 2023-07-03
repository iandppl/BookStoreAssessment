package com.example.bookstore.exception;

public class BookDeletionErrorException extends RuntimeException{
    public BookDeletionErrorException(String message) {
        super(message);
    }
}
