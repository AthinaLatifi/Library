package com.example.bookmanagementsystem.repository;

import com.example.bookmanagementsystem.entity.Aubook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AubooksRepository extends JpaRepository<Aubook, Long> {
}
