package com.example.databaseintegration.controller;



import com.example.databaseintegration.DatabaseintegrationApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DatabaseintegrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookScraperEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testScrapeBooksEndpoint() {
        String url = "http://localhost:" + port + "/books/scrape";

        // Send POST request to trigger scraping
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        // Check response
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("Scraping completed successfully!");
    }
}