package com.example.bookstore.service;

import com.example.bookstore.domain.Author;
import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exception.BookCreationErrorException;
import com.example.bookstore.exception.NoBooksFoundException;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final MapperService mapperService;

    @Autowired
    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            MapperService mapperService
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.mapperService = mapperService;
    }

    @Override
    public boolean createBook(BookDTO bookDTO) {
        try {
            Book book = mapperService.fromDTO(bookDTO);
            Book previousBook = bookRepository.findById(book.getIsbn()).orElse(null);
            if(previousBook != null) throw new BookCreationErrorException("Book is already created");
            bookRepository.save(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateBook(BookDTO bookDTO) {
        try {
            Book book = mapperService.fromDTO(bookDTO);
            Book previousBook = bookRepository.findById(book.getIsbn()).orElse(null);
            if(previousBook == null) throw new NoBooksFoundException("No such book to update");
            bookRepository.save(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<BookDTO> findBooksByTitleOrAuthor(String titleOrAuthor) {
        Set<Book> searchBook = bookRepository.findByTitle(titleOrAuthor)
                .stream().collect(Collectors.toSet());
        Author author = authorRepository.findById(titleOrAuthor).orElse(null);
        if (author != null) {
            searchBook.addAll(bookRepository.findByAuthors(author));
        }
        if (searchBook.isEmpty()) {
            throw new NoBooksFoundException("No books found for " + titleOrAuthor);
        }
        List<BookDTO> bookDTOList = searchBook.stream()
                .map(book -> mapperService.toDTO(book))
                .toList();
        return bookDTOList;
    }

    @Override
    public boolean deleteBook(String isbn) {
        try {
            Book book = bookRepository.findById(isbn).orElse(null);
            book.setAuthors(new ArrayList<>());
            if (book == null) return false;
            bookRepository.delete(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
