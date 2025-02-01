package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.entity.Book;
import com.example.bookmanagementsystem.entity.BooksBorrowed;
import com.example.bookmanagementsystem.repository.BooksBorrowedRepository;
import com.example.bookmanagementsystem.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksBorrowedService {

    @Autowired
    private BooksBorrowedRepository booksBorrowedRepository;
    @Autowired
    private BooksRepository booksRepository;

    public void borrowBook(BooksBorrowed booksBorrowed) {
        booksBorrowedRepository.save(booksBorrowed);
    }

    public List<BooksBorrowed> findByUser_Id(Long userId) {
        return booksBorrowedRepository.findByUser_Id(userId);
    }

    public void save(BooksBorrowed booksBorrowed) {
        booksBorrowedRepository.save(booksBorrowed);
    }

    public void extendReturnDate(Long borrowedId) {
        BooksBorrowed borrowedBook = booksBorrowedRepository.findById(borrowedId).orElse(null);
        if (borrowedBook != null) {
            borrowedBook.setReturnDate(borrowedBook.getReturnDate().plusDays(14));
            booksBorrowedRepository.save(borrowedBook);
        }
    }



    public void returnBook(Long borrowedId) {
        BooksBorrowed borrowedBook = booksBorrowedRepository.findById(borrowedId).orElse(null);
        if (borrowedBook != null) {
            Book book = borrowedBook.getBook();
            book.setStock(book.getStock() + 1);
            booksRepository.save(book);
            booksBorrowedRepository.delete(borrowedBook);
        }
    }

}

