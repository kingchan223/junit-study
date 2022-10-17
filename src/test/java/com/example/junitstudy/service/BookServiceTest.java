package com.example.junitstudy.service;

import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.mail.MailSender;
import com.example.junitstudy.mail.MailSenderStub;
import com.example.junitstudy.web.dto.BookRequest;
import com.example.junitstudy.web.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)//가짜 메모리 환경을 만들기 위한 어노테이션
public class BookServiceTest {
//    @Autowired
//    private BookRepository bookRepository;

    @InjectMocks// 가짜 환경에 있는 익명 객체들을 주입해준다.
    private BookService bookService;

    @Mock// --> 가짜 환경에 익명 객체를 띄운다.
    private BookRepository bookRepository;
    @Mock// --> 가짜 환경에 익명 객체를 띄운다.
    private MailSender mailSender;
    @Test
    void 책_등록_DB_Layer까지_테스트(){

        // 1.given
//        BookRequest bookRequest = BookRequest.builder().title("junit").author("lee").build();
        // 2.stub
//        MailSenderStub mailSenderStub = new MailSenderStub();
        // 3.when
//        BookService bookService = new BookService(bookRepository, mailSenderStub);
//        BookResponse bookResp = bookService.책_등록하기(bookRequest);
        // 4.then
//        assertEquals(bookResp.getTitle(), bookRequest.getTitle());
//        assertEquals(bookResp.getAuthor(), bookRequest.getAuthor());
    }/* 문제점 : 서비스만 테스트하고 싶은데 리퍼지토리 레이어도 테스트하고 있음*/

    @Test
    void 책_등록(){

        // 1.given
        BookRequest bookRequest = BookRequest.builder().title("junit").author("lee").build();
        // 2.stub (가설 정의)
        when(bookRepository.save(any())).thenReturn(bookRequest.toEntity());
        when(mailSender.send()).thenReturn(true);
        // 3.when
        BookResponse bookResp = bookService.책_등록하기(bookRequest);
        // 4.then
        assertThat(bookResp.getTitle()).isEqualTo(bookRequest.getTitle());
        assertThat(bookResp.getAuthor()).isEqualTo(bookRequest.getAuthor());
    }/* 문제점 : 서비스만 테스트하고 싶은데 리퍼지토리 레이어도 테스트하고 있음*/
}
