package com.example.bookmanagement.controller;

import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.dto.CreateBookRequest;
import com.example.bookmanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        // Arrange
        BookDTO book = new BookDTO();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublishYear(2024);

        when(bookService.getAllBooks()).thenReturn(List.of(book));

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Test Author"));
    }

    @Test
    void createBook_WithValidData_ShouldReturnCreatedBook() throws Exception {
        // Arrange
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("New Book");
        request.setAuthor("New Author");
        request.setIsbn("1234567890");
        request.setPublishYear(2024);

        BookDTO response = new BookDTO();
        response.setId(1L);
        response.setTitle(request.getTitle());
        response.setAuthor(request.getAuthor());
        response.setIsbn(request.getIsbn());
        response.setPublishYear(request.getPublishYear());

        when(bookService.createBook(any(CreateBookRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void createBook_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CreateBookRequest request = new CreateBookRequest();
        // Missing required fields

        // Act & Assert
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
} 