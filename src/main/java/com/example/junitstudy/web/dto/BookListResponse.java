package com.example.junitstudy.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookListResponse {
    List<BookResponse> items;

    @Builder
    public BookListResponse(List<BookResponse> items) {
        this.items = items;
    }
}
