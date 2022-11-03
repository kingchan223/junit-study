package com.example.junitstudy.web.dto;

import com.example.junitstudy.domain.Book;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class BookRequest {

    @NotBlank
    @NotNull
    @Size(min = 1, max = 50)
    String title;

    @NotBlank
    @NotNull
    @Size(min = 1, max = 20)
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
