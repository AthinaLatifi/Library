package com.example.bookmanagementsystem.service;


import com.example.bookmanagementsystem.entity.Book;
import com.example.bookmanagementsystem.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public Book findByIsbn(String isbn) {
        return booksRepository.findByIsbn(isbn);
    }

    public Book updateBook(Book book) {
        // Check if the book exists
        if (booksRepository.existsById(book.getId())) {
            return booksRepository.save(book); // Save the updated book
        } else {
            throw new RuntimeException("Book not found with id: " + book.getId());
        }
    }

    public void deleteBookByIsbn(String isbn) {
        // Find the book by ISBN
        Book book = booksRepository.findByIsbn(isbn);
        if (book != null) {
            booksRepository.delete(book);
        } else {
            throw new RuntimeException("Book not found with ISBN: " + isbn);
        }
    }

    public void save(Book book) {
        booksRepository.save(book); // Save the book to the database
    }

    public List<Book> getAllAvailableBooks() {
        return booksRepository.findAllByStockGreaterThan(0); // Fetch books with stock greater than 0
    }


}
