package com.example.bookstore.controller;

import com.example.bookstore.BookStoreApplication;
import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exception.BookCreationErrorException;
import com.example.bookstore.exception.BookDeletionErrorException;
import com.example.bookstore.exception.BookUpdateErrorException;
import com.example.bookstore.exception.NoBooksFoundException;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = BookStoreApplication.class)
public class BookControllerTest {
    @Mock
    private BookService bookService;

    private BookController bookController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService);
    }

    @Test
    public void testFindBookByTitleOrAuthor_withResult_expectResultToBeReturned(){
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
        given(bookService.findBooksByTitleOrAuthor("John")).willReturn(List.of(bookDTO));

        List<BookDTO> result = bookController.findBookByTitleOrAuthor("John").getBody();

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
    public void testFindBookByTitleOrAuthor_withNoResult_expectNoBooksFoundExceptionToBeThrown(){
        given(bookService.findBooksByTitleOrAuthor("John")).willThrow(new NoBooksFoundException("Test"));

        Assertions.assertThrows(
            NoBooksFoundException.class,
            ()-> bookController.findBookByTitleOrAuthor("John")
        );
    }

    @Test
    public void testCreateBook_withFalseReturn_expectBookCreateExceptionToBeThrown(){
        given(bookService.createBook(any())).willThrow(new BookCreationErrorException("Test"));

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

        Assertions.assertThrows(
            BookCreationErrorException.class,
            ()-> bookController.createBook(bookDTO)
        );
    }

    @Test
    public void testCreateBook_withTrueReturn_expectTrue(){
        given(bookService.createBook(any())).willReturn(true);

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

        boolean result = bookController.createBook(bookDTO).getBody();

        Assertions.assertTrue(result);
    }

    @Test
    public void testUpdateBook_withException_expectBookCreateExceptionToBeThrown(){
        given(bookService.updateBook(any())).willThrow(new BookUpdateErrorException("Test"));

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

        Assertions.assertThrows(
            BookUpdateErrorException.class,
            ()-> bookController.updateBook(bookDTO)
        );
    }

    @Test
    public void testUpdateBook_withTrueReturn_expectTrue(){
        given(bookService.updateBook(any())).willReturn(true);

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

        boolean result = bookController.updateBook(bookDTO).getBody();

        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteBook_withFalseReturn_expectBookCreateExceptionToBeThrown(){
        given(bookService.deleteBook(any())).willThrow(new BookDeletionErrorException("Test"));

        Assertions.assertThrows(
            BookDeletionErrorException.class,
            ()-> bookController.deleteBook("12345")
        );
    }

    @Test
    public void testDeleteBook_withTrueReturn_expectTrue(){
        given(bookService.deleteBook(any())).willReturn(true);

        boolean result = bookController.deleteBook("12345").getBody();

        Assertions.assertTrue(result);
    }
}
