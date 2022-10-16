package com.example.junitstudy.web.dto;

import com.example.junitstudy.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookResponse {
    private Long id;
    private String title;
    private String author;

    public static BookResponse toDto(Book bookPS) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.id = bookPS.getId();
        bookResponse.title = bookPS.getTitle();
        bookResponse.author = bookPS.getAuthor();
        return bookResponse;

    }
}
