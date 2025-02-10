package com.example.bookmanagement.integration;

import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.dto.CreateBookRequest;
import com.example.bookmanagement.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void createAndRetrieveBook() throws Exception {
        // Create book
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Integration Test Book");
        request.setAuthor("Integration Author");
        request.setIsbn("9876543210");
        request.setPublishYear(2024);

        String bookJson = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Integration Test Book"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookDTO createdBook = objectMapper.readValue(bookJson, BookDTO.class);

        // Retrieve book
        mockMvc.perform(get("/api/books/{id}", createdBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdBook.getId()))
                .andExpect(jsonPath("$.title").value("Integration Test Book"))
                .andExpect(jsonPath("$.author").value("Integration Author"));
    }
} 