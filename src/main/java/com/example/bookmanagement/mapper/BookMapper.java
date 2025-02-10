package com.example.bookmanagement.mapper;

import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.dto.CreateBookRequest;
import com.example.bookmanagement.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDTO(Book book);
    Book toEntity(CreateBookRequest createBookRequest);
    void updateBookFromDTO(CreateBookRequest createBookRequest, @MappingTarget Book book);
} 