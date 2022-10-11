package com.example.junitstudy.web.dto;

import com.example.junitstudy.domain.Book;
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
}
