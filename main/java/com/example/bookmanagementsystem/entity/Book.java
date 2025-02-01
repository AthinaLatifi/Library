package com.example.bookmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @ColumnDefault("0")
    @Column(name = "stock")
    private Integer stock;

    @Column(name = "summary")
    private String summary;

    @Column(name = "author")
    private String author;

    @Column(name = "borrow_count")
    private Integer borrowCount;

    @OneToMany(mappedBy = "book")
    private Set<Aubook> aubooks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<BooksBorrowed> booksBorroweds = new LinkedHashSet<>();

}