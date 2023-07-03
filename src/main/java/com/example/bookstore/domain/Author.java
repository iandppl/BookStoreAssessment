package com.example.bookstore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "AUTHOR")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "BIRTHDAY")
    private String birthday;

    @ManyToMany(mappedBy = "authors", cascade = { CascadeType.ALL })
    private List<Book> books = new ArrayList<>();
}
