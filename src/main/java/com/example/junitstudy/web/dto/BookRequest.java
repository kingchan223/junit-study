package com.example.junitstudy.web.dto;

import com.example.junitstudy.domain.Book;
import lombok.Builder;
import lombok.Data;

@Data
public class BookRequest {
    String title;
    String author;

    public Book toEntity() {
        return Book.builder()
                .title(this.title)
                .author(this.author).build();
    }

    @Builder
    public BookRequest(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
