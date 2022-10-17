package com.example.junitstudy.service;

import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.mail.MailSenderStub;
import com.example.junitstudy.web.dto.BookRequest;
import com.example.junitstudy.web.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void 책_등록_DB_Layer까지_테스트(){

        // 1.given
        BookRequest bookRequest = BookRequest.builder().title("junit").author("lee").build();
        // 2.stub
        MailSenderStub mailSenderStub = new MailSenderStub();
        // 3.when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookResponse bookResp = bookService.책_등록하기(bookRequest);
        // 4.then
        assertEquals(bookResp.getTitle(), bookRequest.getTitle());
        assertEquals(bookResp.getAuthor(), bookRequest.getAuthor());
    }/* 문제점 : 서비스만 테스트하고 싶은데 리퍼지토리 레이어도 테스트하고 있음*/
}
