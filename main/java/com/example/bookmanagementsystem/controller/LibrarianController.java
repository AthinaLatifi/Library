package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.entity.Aubook;
import com.example.bookmanagementsystem.entity.Author;
import com.example.bookmanagementsystem.entity.Book;
import com.example.bookmanagementsystem.repository.AubooksRepository;
import com.example.bookmanagementsystem.repository.AuthorRepository;
import com.example.bookmanagementsystem.repository.BooksRepository;
import com.example.bookmanagementsystem.service.AuthorService;
import com.example.bookmanagementsystem.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LibrarianController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BooksService booksService;
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AubooksRepository aubooksRepository;

    @GetMapping("librarian")
    public String librarian(ModelMap model) {
        List<Book> books = booksService.getAllBooks();
        model.addAttribute("books", books);
        return "librarian";
    }


    @GetMapping("add-book")
    public String showAddBookForm(ModelMap model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book,
                          @RequestParam String authorName) {
        System.out.println("Saving book: " + book);

        // Check if the author exists
        Author existingAuthor = authorService.findByName(authorName);
        if (existingAuthor == null) {
            // If the author does not exist, create a new author
            Author newAuthor = new Author();
            newAuthor.setName(authorName);
            authorRepository.save(newAuthor);
            Book newBook = new Book();
            newBook.setTitle(book.getTitle());
            newBook.setIsbn(book.getIsbn());
            newBook.setSummary(book.getSummary());
            newBook.setStock(book.getStock());
            newBook.setBorrowCount(book.getBorrowCount());
            booksRepository.save(newBook);
            Aubook aubook = new Aubook();
            aubook.setAuthor(newAuthor);
            aubook.setBook(book);
            aubooksRepository.save(aubook);
        } else {
            // If the author exists, add them to the book
            Book newBook = new Book();
            newBook.setTitle(book.getTitle());
            newBook.setIsbn(book.getIsbn());
            newBook.setSummary(book.getSummary());
            newBook.setStock(book.getStock());
            newBook.setBorrowCount(book.getBorrowCount());
            booksRepository.save(newBook);
            Aubook aubook = new Aubook();
            aubook.setAuthor(existingAuthor);
            aubook.setBook(book);
            aubooksRepository.save(aubook);
        }
        return "redirect:/librarian";
    }

    @GetMapping("books")
    public String manageBooks(ModelMap model) {
        List<Book> books = booksService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/edit")
    public String showEditBookForm(@RequestParam("isbn") String isbn, ModelMap model) {
        Book selectedBook = booksService.findByIsbn(isbn);
        List<Book> books = booksService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("selectedBook", selectedBook);
        return "edit";
    }


    @PostMapping("/update-book")
    public String updateBook(@RequestParam("id") Long id,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("stock") Integer stock,
                             @RequestParam("summary") String summary) {
        // Create a new Books object and set its properties
        Book book = new Book();
        book.setId(id);
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setStock(stock);
        book.setSummary(summary);


        booksService.updateBook(book);

        return "redirect:/books";
    }

    @PostMapping("/delete-book")
    public String deleteBook(@RequestParam("isbn") String isbn) {
        booksService.deleteBookByIsbn(isbn);
        return "redirect:/books";
    }

    @PostMapping("/save-book")
    public String saveBook(@ModelAttribute Book book) {
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            return "error";
        }
        booksService.save(book);
        return "redirect:/books";
    }

}
