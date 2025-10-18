package com.example.databaseintegration.controller;

import com.example.databaseintegration.model.Book;
import com.example.databaseintegration.repository.BookRepository;
import com.example.databaseintegration.service.BookScraperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookScraperService bookScraperService;

    public BookController(BookRepository bookRepository, BookScraperService bookScraperService) {
        this.bookRepository = bookRepository;
        this.bookScraperService = bookScraperService;
    }

    // Render book list page
    @GetMapping("/view")
    public String viewBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    // Manual scraper trigger (from button)
    @PostMapping("/scrape")
    public String scrapeBooksNow(Model model) {
        String message = bookScraperService.scrapeBooksManually();
        model.addAttribute("message", message + " (Last scrape: " + java.time.LocalDateTime.now() + ")");
        model.addAttribute("books", bookRepository.findAll());
        return "books"; // redirect to list with message
    }
}



