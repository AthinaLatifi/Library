package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.entity.Book;
import com.example.bookmanagementsystem.entity.User;
import com.example.bookmanagementsystem.service.*;
import com.example.bookmanagementsystem.service.PdfExportService;
import com.example.bookmanagementsystem.service.BooksService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private PdfExportService pdfExportService;
    @Autowired
    private BooksService booksService;


    @GetMapping("admin")
    public String adminHomePage(ModelMap model) {
        model.addAttribute("totalNumberOfBooks", analyticsService.getTotalNumberOfBooks());
        model.addAttribute("totalStock", analyticsService.getTotalStock());
        model.addAttribute("lowStockBooks", analyticsService.getLowStockBooks(3));
        List<Book> mostBorrowedBooks = analyticsService.getMostBorrowedBooks();
        model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);
        return "admin";
    }

    @GetMapping("/musers")
    public String listUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "musers";
    }

    @GetMapping("/add")
    public String showAddUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "adduser";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/musers";
    }

    @GetMapping("edit/{id}")
    public String showEditUserForm(@PathVariable Long id, ModelMap model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "edituser";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user) {
        User existingUser = userService.findUserById(user.getId());

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        }

        userService.saveUser(user);
        return "redirect:/musers";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/musers";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportPdf() {
        List<User> users = userService.findAllUsers();
        List<Book> books = booksService.getAllBooks();

        try {
            byte[] pdfContents = pdfExportService.exportToPdf(users, books);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "attachment; filename=analytics_report.pdf");
            return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
        } catch (IOException | DocumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



