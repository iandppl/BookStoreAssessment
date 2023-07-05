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
        try{
            List<BookDTO> searchResult = bookService.findBooksByTitleOrAuthor(titleOrAuthorName);
            return ResponseEntity.ok(searchResult);
        }catch (Exception e){
            throw new NoBooksFoundException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> createBook(@RequestBody BookDTO bookDTO) {
        try{
            boolean result = bookService.createBook(bookDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            throw new BookCreationErrorException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Boolean> updateBook(@RequestBody BookDTO bookDTO) {
        try{
            boolean result = bookService.updateBook(bookDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            throw new BookUpdateErrorException(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteBook(@RequestParam String isbn) {
        try{
            boolean result = bookService.deleteBook(isbn);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            throw new BookDeletionErrorException(e.getMessage());
        }

    }
}
