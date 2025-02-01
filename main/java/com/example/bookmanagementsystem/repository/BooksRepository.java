package com.example.bookmanagementsystem.repository;

import com.example.bookmanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BooksRepository extends JpaRepository<Book, Long>
{
    Book findByIsbn(String isbn);
    List<Book> findAllByStockGreaterThan(int stock);
    List<Book> findByStockLessThan(int threshold);
    @Query("SELECT b FROM Book b ORDER BY b.borrowCount DESC")
    List<Book> findMostBorrowedBooks();
}
