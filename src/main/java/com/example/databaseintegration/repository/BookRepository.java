package com.example.databaseintegration.repository;

import com.example.databaseintegration.model.Book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
	Optional<Book> findByTitle(String title);

}


