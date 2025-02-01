package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.AppScopeBean;
import com.example.bookmanagementsystem.entity.Book;
import com.example.bookmanagementsystem.entity.BooksBorrowed;
import com.example.bookmanagementsystem.entity.User;
import com.example.bookmanagementsystem.repository.BooksBorrowedRepository;
import com.example.bookmanagementsystem.service.BooksBorrowedService;
import com.example.bookmanagementsystem.service.BooksService;
import com.example.bookmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private BooksService booksService;

    @Autowired
    private BooksBorrowedService booksBorrowedService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppScopeBean applicationScope;
    @Autowired
    private BooksBorrowedRepository booksBorrowedRepository;


    @GetMapping("user")
    public String user(ModelMap model, HttpSession session) {
        List<Book> availableBooks = booksService.getAllAvailableBooks();
        model.addAttribute("availableBooks", availableBooks);

        // Fetch borrowed books for the logged-in user
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/mybooks")
    public String myBooks(ModelMap model, HttpSession session) {
        // Log all session attributes for debugging
        System.out.println("Session ID: " + session.getId());
        System.out.println("Session Attributes: ");
        session.getAttributeNames().asIterator().forEachRemaining(name -> {
            System.out.println(name + ": " + session.getAttribute(name));
        });

        User user = (User) session.getAttribute("currentUser");
        List<BooksBorrowed> borrowedBooks = booksBorrowedService.findByUser_Id(user.getId());
        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("user", user);
        return "mybooks";
    }
    @GetMapping("borrow")
    public String borrow(@RequestParam String isbn,
                         ModelMap model) {
        Book book = booksService.findByIsbn(isbn);
        model.addAttribute("book", book);
        return "borrow";
    }
    @PostMapping("borrow")
    public String borrowBook(@RequestParam String isbn,
                             HttpSession session) {
        // Find the book by ISBN
        Book book = booksService.findByIsbn(isbn);

        // Check if the book is available
        if (book != null && book.getStock() > 0) {
            // Create a new BooksBorrowed entry
            BooksBorrowed booksBorrowed = new BooksBorrowed();
            booksBorrowed.setBook(book);
            booksBorrowed.setUser((User) session.getAttribute("currentUser"));
            booksBorrowedRepository.save(booksBorrowed);
            // Fetch the User object based on the username
            booksBorrowed.setBorrowedDate(LocalDate.now());
            booksBorrowed.setReturnDate(LocalDate.now().plusDays(14)); // Set return date to 14 days later
            // Decrease the stock of the book
            book.setStock(book.getStock() - 1);
            booksService.save(book);
        }

        return "redirect:/user";
    }

    @PostMapping("/extend-return-date")
    public String extendReturnDate(@RequestParam Long borrowedId) {
        booksBorrowedService.extendReturnDate(borrowedId);
        return "redirect:/user";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam Long borrowedId) {
        booksBorrowedService.returnBook(borrowedId);
        return "redirect:/user";
    }
}

