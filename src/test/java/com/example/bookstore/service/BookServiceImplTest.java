package com.example.bookstore.service;

import com.example.bookstore.BookStoreApplication;
import com.example.bookstore.domain.Author;
import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exception.NoBooksFoundException;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = BookStoreApplication.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    private MapperService mapperService;

    private BookService bookService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        mapperService = new MapperServiceImpl();
        bookService = new BookServiceImpl(bookRepository, authorRepository, mapperService);
    }

    @Test
    public void createOrUpdateBooks_withSuccessSaving_expectTrue(){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("John");
        authorDTO.setBirthday("12/12/1960");
        BookDTO bookDTO  = new BookDTO();
        bookDTO.setAuthorDTOList(List.of(authorDTO));
        bookDTO.setIsbn("12345");
        bookDTO.setTitle("Little Mermaid");
        bookDTO.setYear(1980);
        bookDTO.setPrice(12.99);
        bookDTO.setGenre("Fairy Tale");

        Author foundAuthor = new Author();
        foundAuthor.setName("John");
        foundAuthor.setBirthday("12/12/1960");
        Book book  = new Book();
        book.setAuthors(List.of(foundAuthor));
        book.setIsbn("12345");
        book.setTitle("Little Mermaid");
        book.setYear(1980);
        book.setPrice(12.99);
        book.setGenre("Fairy Tale");

        given(bookRepository.save(any())).willReturn(book);

        boolean result = bookService.createOrUpdateBook(bookDTO);

        Assertions.assertTrue(result);
    }

    @Test
    public void findBooksByTitleOrAuthor_withNoResult_expectNoBooksFoundException(){
        given(bookRepository.findByTitle(any())).willReturn(new ArrayList<>());
        given(authorRepository.findById(any())).willReturn(Optional.ofNullable(null));

        Assertions.assertThrows(
            NoBooksFoundException.class,
            ()-> bookService.findBooksByTitleOrAuthor("Test")
        );
    }

    @Test
    public void findBooksByTitleOrAuthor_withAuthorResult_expectBookSizeToBe1(){
        given(bookRepository.findByTitle(any())).willReturn(new ArrayList<>());
        Author foundAuthor = new Author();
        foundAuthor.setName("John");
        foundAuthor.setBirthday("12/12/1960");
        given(authorRepository.findById(any())).willReturn(Optional.of(foundAuthor));
        Book book  = new Book();
        book.setAuthors(List.of(foundAuthor));
        book.setIsbn("12345");
        book.setTitle("Little Mermaid");
        book.setYear(1980);
        book.setPrice(12.99);
        book.setGenre("Fairy Tale");
        given(bookRepository.findByAuthors(any())).willReturn(List.of(book));

        List<BookDTO> result = bookService.findBooksByTitleOrAuthor("John");

        Assertions.assertEquals(result.size(), 1);
        BookDTO firstBook = result.get(0);

        Assertions.assertEquals(firstBook.getIsbn(), "12345");
        Assertions.assertEquals(firstBook.getAuthorDTOList().size(), 1);
        Assertions.assertEquals(firstBook.getTitle(), "Little Mermaid");
        Assertions.assertEquals(firstBook.getYear(), 1980);
        Assertions.assertEquals(firstBook.getPrice(), 12.99);
        Assertions.assertEquals(firstBook.getGenre(), "Fairy Tale");
    }

    @Test
    public void findBooksByTitleOrAuthor_withTitleResult_expectBookSizeToBe1(){
        Author foundAuthor = new Author();
        foundAuthor.setName("John");
        foundAuthor.setBirthday("12/12/1960");
        given(authorRepository.findById(any())).willReturn(Optional.ofNullable(null));
        Book book  = new Book();
        book.setAuthors(List.of(foundAuthor));
        book.setIsbn("12345");
        book.setTitle("Little Mermaid");
        book.setYear(1980);
        book.setPrice(12.99);
        book.setGenre("Fairy Tale");
        given(bookRepository.findByTitle(any())).willReturn(List.of(book));

        List<BookDTO> result = bookService.findBooksByTitleOrAuthor("John");

        Assertions.assertEquals(result.size(), 1);
        BookDTO firstBook = result.get(0);

        Assertions.assertEquals(firstBook.getIsbn(), "12345");
        Assertions.assertEquals(firstBook.getAuthorDTOList().size(), 1);
        Assertions.assertEquals(firstBook.getTitle(), "Little Mermaid");
        Assertions.assertEquals(firstBook.getYear(), 1980);
        Assertions.assertEquals(firstBook.getPrice(), 12.99);
        Assertions.assertEquals(firstBook.getGenre(), "Fairy Tale");
    }

    @Test
    public void deleteBook_withInvalidIsbn_expectFalse(){
        given(bookRepository.findById(any())).willReturn(Optional.ofNullable(null));

        boolean result = bookService.deleteBook("1234");

        Assertions.assertFalse(result);
    }

    @Test
    public void deleteBook_withValidIsbn_expectTrue(){
        Author foundAuthor = new Author();
        foundAuthor.setName("John");
        foundAuthor.setBirthday("12/12/1960");
        Book book  = new Book();
        book.setAuthors(List.of(foundAuthor));
        book.setIsbn("12345");
        book.setTitle("Little Mermaid");
        book.setYear(1980);
        book.setPrice(12.99);
        book.setGenre("Fairy Tale");
        given(bookRepository.findById(any())).willReturn(Optional.of(book));

        boolean result = bookService.deleteBook("12345");

        Assertions.assertTrue(result);
    }
}
