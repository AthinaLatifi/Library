package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.entity.Book;
import com.example.bookmanagementsystem.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnalyticsService {
    @Autowired
    private BooksRepository bookRepository;

    public int getTotalNumberOfBooks() {
        return (int) bookRepository.count();
    }

    public int getTotalStock() {
        return bookRepository.findAll().stream().mapToInt(Book::getStock).sum();
    }

    public List<Book> getLowStockBooks(int threshold) {
        return bookRepository.findByStockLessThan(threshold);
    }

    public List<Book> getMostBorrowedBooks() {
        return bookRepository.findMostBorrowedBooks();
    }

}
