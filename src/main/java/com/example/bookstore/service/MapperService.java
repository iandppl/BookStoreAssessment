package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;

public interface MapperService {
    public Book fromDTO(BookDTO bookDTO);
    public BookDTO toDTO(Book book);
}
