package com.example.bookmanagementsystem.repository;

import com.example.bookmanagementsystem.entity.BooksBorrowed;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BooksBorrowedRepository extends JpaRepository<BooksBorrowed, Long> {
    List<BooksBorrowed> findByUser_Id(Long id);
//    List<BooksBorrowed> findByUsername(String username);
}
