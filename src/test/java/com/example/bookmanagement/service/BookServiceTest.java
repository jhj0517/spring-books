package com.example.bookmanagement.service;

import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.dto.CreateBookRequest;
import com.example.bookmanagement.entity.Book;
import com.example.bookmanagement.exception.BookNotFoundException;
import com.example.bookmanagement.mapper.BookMapper;
import com.example.bookmanagement.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        // Arrange
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publishYear(2024)
                .build();

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Test Author");

        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        // Act
        List<BookDTO> result = bookService.getAllBooks();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book");
        verify(bookRepository).findAll();
    }

    @Test
    void getBookById_WithValidId_ShouldReturnBook() {
        // Arrange
        Long id = 1L;
        Book book = Book.builder()
                .id(id)
                .title("Test Book")
                .build();
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        bookDTO.setTitle("Test Book");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        // Act
        BookDTO result = bookService.getBookById(id);

        // Assert
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo("Test Book");
    }

    @Test
    void getBookById_WithInvalidId_ShouldThrowException() {
        // Arrange
        Long id = 999L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bookService.getBookById(id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining(String.valueOf(id));
    }
} 