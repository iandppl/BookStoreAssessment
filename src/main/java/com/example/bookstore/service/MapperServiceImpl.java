package com.example.bookstore.service;

import com.example.bookstore.domain.Author;
import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperServiceImpl implements MapperService{
    @Override
    public Book fromDTO(BookDTO bookDTO) {
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setYear(bookDTO.getYear());
        book.setPrice(bookDTO.getPrice());
        book.setGenre(bookDTO.getGenre());
        List<Author> authorList = bookDTO.getAuthorDTOList().stream().map(authorDTO -> {
            List<Book> bookList = new ArrayList<>();
            bookList.add(book);
            Author author = new Author();
            author.setName(authorDTO.getName());
            author.setBirthday(authorDTO.getBirthday());
            author.setBooks(bookList);
            return author;
        }).toList();
        book.setAuthors(authorList);
        return book;
    }

    @Override
    public BookDTO toDTO(Book book) {
        List<AuthorDTO> authors = book.getAuthors().stream().map(author1 ->
                new AuthorDTO(author1.getName(), author1.getBirthday())
        ).toList();
        return new BookDTO(book.getIsbn(), book.getTitle(), authors, book.getYear(), book.getPrice(), book.getGenre());
    }
}
