package com.example.bookstore.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "BOOK")
public class Book {
    @Id
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "TITLE")
    private String title;

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(
            name = "BOOKS_AUTHORS",
            joinColumns = @JoinColumn(name = "ISBN"),
            inverseJoinColumns = @JoinColumn(name = "ID")
    )
    private List<Author> authors;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "GENRE")
    private String genre;
}
