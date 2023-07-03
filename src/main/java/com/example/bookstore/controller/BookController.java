package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exception.BookCreationException;
import com.example.bookstore.exception.BookDeletionErrorException;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(
        BookService bookService
    ) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> createBook(@RequestParam String titleOrAuthorName) {
        List<BookDTO> searchResult = bookService.findBooksByTitleOrAuthor(titleOrAuthorName);
        return ResponseEntity.ok(searchResult);
    }

    @PostMapping
    public ResponseEntity<Boolean> createBook(@RequestBody BookDTO bookDTO) {
        boolean result = bookService.createOrUpdateBook(bookDTO);
        if(!result) {
            throw new BookCreationException("Unable to create book please check request body");
        }
        return ResponseEntity.ok(true);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteBook(@RequestParam String isbn) {
        boolean result = bookService.deleteBook(isbn);
        if(!result) {
            throw new BookDeletionErrorException("Unable to delete book");
        }
        return ResponseEntity.ok(true);
    }
}
