package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;

import java.util.List;

public interface BookService {
    boolean createOrUpdateBook(BookDTO bookDTO);
    List<BookDTO> findBooksByTitleOrAuthor(String titleOrAuthor);
    boolean deleteBook(String isbn);
}
