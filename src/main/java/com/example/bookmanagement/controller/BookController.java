package com.example.bookmanagement.controller;

import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.dto.CreateBookRequest;
import com.example.bookmanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book Management API")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID")
    public BookDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new book")
    public BookDTO createBook(@Valid @RequestBody CreateBookRequest request) {
        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody CreateBookRequest request) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a book")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
} 