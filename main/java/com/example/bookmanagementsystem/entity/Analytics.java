package com.example.bookmanagementsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Analytics {
    private int totalUsers;
    private int totalBooks;
    private int totalLoans;

    // Constructor
    public Analytics(int totalUsers, int totalBooks, int totalLoans) {
        this.totalUsers = totalUsers;
        this.totalBooks = totalBooks;
        this.totalLoans = totalLoans;
    }
    public int getTotalUsers() {
        return totalUsers;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public int getTotalLoans() {
        return totalLoans;
    }
}