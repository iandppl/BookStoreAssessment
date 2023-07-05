package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exception.BookCreationErrorException;
import com.example.bookstore.exception.BookDeletionErrorException;
import com.example.bookstore.exception.BookUpdateErrorException;
import com.example.bookstore.exception.NoBooksFoundException;
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
    public ResponseEntity<List<BookDTO>> findBookByTitleOrAuthor(@RequestParam String titleOrAuthorName) {
        List<BookDTO> searchResult = bookService.findBooksByTitleOrAuthor(titleOrAuthorName);
        if(searchResult.isEmpty()){
            throw new NoBooksFoundException("No books found for this title/author name");
        }
        return ResponseEntity.ok(searchResult);
    }

    @PostMapping
    public ResponseEntity<Boolean> createBook(@RequestBody BookDTO bookDTO) {
        boolean result = bookService.createBook(bookDTO);
        if(!result) {
            throw new BookCreationErrorException("Book is already created");
        }
        return ResponseEntity.ok(true);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateBook(@RequestBody BookDTO bookDTO) {
        boolean result = bookService.updateBook(bookDTO);
        if(!result) {
            throw new BookUpdateErrorException("Error in updating book. Please check if book have been created");
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
