package com.example.databaseintegration.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String price;

    // Default constructor
    public Book() {
    }

    // Constructor with fields
    public Book(String title, String author, String price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
}

