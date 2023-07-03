package com.example.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String isbn;
    private String title;
    private List<AuthorDTO> authorDTOList;
    private int year;
    private Double price;
    private String genre;
}
