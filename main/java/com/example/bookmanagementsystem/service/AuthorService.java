package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.entity.Author;
import com.example.bookmanagementsystem.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

}
