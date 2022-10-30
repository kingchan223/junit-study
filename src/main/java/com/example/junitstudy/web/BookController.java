package com.example.junitstudy.web;

import com.example.junitstudy.service.BookService;
import com.example.junitstudy.web.dto.BookRequest;
import com.example.junitstudy.web.dto.BookResponse;
import com.example.junitstudy.web.dto.CMRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public ResponseEntity<?> save(@Valid @RequestBody BookRequest bookRequest, BindingResult bindingResult){
                ;
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) errorMap.put(fe.getField(), fe.getDefaultMessage());
            throw new RuntimeException(errorMap.toString());
        }

        return new ResponseEntity<>(
                CMRespDTO.builder().code(1).msg("save book").body(bookService.책_등록하기(bookRequest)).build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList(){
        return new ResponseEntity<>(
                CMRespDTO.builder().code(1).msg("get book list").body(bookService.책_목록보기()).build(),
                HttpStatus.OK);
    }

    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id){
        return new ResponseEntity<>(
                CMRespDTO.builder().code(1).msg("get book").body(
                        bookService.책_한건보기(id)
                ).build(),
                HttpStatus.OK);
    }

    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BookRequest bookRequest){
        return new ResponseEntity<>(
                CMRespDTO.builder().code(1).msg("update book").body(
                        bookService.책_수정하기(id, bookRequest)
                ).build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        bookService.책_삭제하기(id);
        return new ResponseEntity<>(
                CMRespDTO.builder().code(1).msg("delete book").body(
                        null
                ).build(),
                HttpStatus.OK);
    }
}
