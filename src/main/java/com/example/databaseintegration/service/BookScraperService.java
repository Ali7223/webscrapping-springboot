package com.example.databaseintegration.service;


import com.example.databaseintegration.model.Book;
import com.example.databaseintegration.repository.BookRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookScraperService {

    private final BookRepository bookRepository;

    public BookScraperService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Runs every hour
    @Scheduled(fixedRate = 3600000)
    public void scrapeBooks() {
    	String url = "http://books.toscrape.com/catalogue/category/books_1/index.html";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements books = doc.select(".book-item"); // Adjust selectors

            for (Element bookElement : books) {
                String title = bookElement.select(".title").text();
                String author = bookElement.select(".author").text();
                String price = bookElement.select(".price").text();

                Book book = new Book(title, author.isEmpty() ? "Unknown" : author, price);
                bookRepository.save(book);
            }

            System.out.println("Scraping done!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public String scrapeBooksManually() {
        try {
            Document doc = Jsoup.connect("https://books.toscrape.com/").get();

            Elements books = doc.select(".product_pod");

            // Fetch all existing titles once
            Set<String> existingTitles = bookRepository.findAll()
                                                       .stream()
                                                       .map(Book::getTitle)
                                                       .collect(Collectors.toSet());

            List<Book> newBooks = new ArrayList<>();

            for (Element bookElement : books) {
                String title = bookElement.select("h3 a").attr("title");
                String price = bookElement.select(".price_color").text();

                if (existingTitles.contains(title)) continue; // skip duplicates

                Book book = new Book(title, "Unknown", price);
                newBooks.add(book);
            }

            // Save all new books in one batch
            if (!newBooks.isEmpty()) {
                bookRepository.saveAll(newBooks);
            }

            return "Scraping completed successfully! Added " + newBooks.size() + " new books.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Scraping failed: " + e.getMessage();
        }
    }
}
