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
                CMRespDTO.builder().code(1).msg("save book").body(bookService.책_목록보기()).build(),
                HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(){

        return null;
    }

    @PutMapping("/api/v1/book")
    public ResponseEntity<?> update(){
        return null;
    }

    @DeleteMapping("/api/v1/book")
    public ResponseEntity<?> delete(){
        return null;
    }
}
