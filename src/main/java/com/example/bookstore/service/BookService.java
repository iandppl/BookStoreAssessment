package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;

import java.util.List;

public interface BookService {
    boolean createBook(BookDTO bookDTO);
    boolean updateBook(BookDTO bookDTO);
    List<BookDTO> findBooksByTitleOrAuthor(String titleOrAuthor);
    boolean deleteBook(String isbn);
}
