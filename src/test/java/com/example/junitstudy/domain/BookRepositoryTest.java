package com.example.junitstudy.domain;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest /* --> DB와 관련된 컴포넌트만 메모리에 로딩 (즉 controller, service layer 는 메모리에 로딩 X) */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//DataJpaTest 는 in-memory db만 지원. 얘를 넣어줘야 외부 db도 사용가능
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    //@BeforeAll // 테스트 시작 전에 한번만 실행
    @BeforeEach  // 모든 각 테스트 시작 전에 한번씩 실행
    void 데이터_준비() {
        String title = "junit";
        String author = "chan";
        Book book = Book.builder()
                .title(title)
                .author(author).build();

        bookRepository.save(book);
    }// 얘는 메서드가 끝난다고 트랜잭션 종료 안됨. --> rollback 안됨
     // @BeforeEach 는 바로 다음에 실행되는 메서드까지만 트랜잭션이 묶이게 된다.

    // 1. 책 등록
    @Test
    void 책_등록() {
        //given (데이터 준비)
        String title = "junit5";
        String author = "lee";
        Book book = Book.builder()
                .title(title)
                .author(author).build();

        //when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        //then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // transaction close --> rollback(init saved data)

    @Test
    void 책_목록_조회() {
        //given (데이터 준비)
        String title = "junit";
        String author = "chan";

        //when (테스트 실행)
        List<Book> booksPS = bookRepository.findAll();

        //then (검증)
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
        assertEquals(1, booksPS.size());
    } // transaction close --> rollback(init saved data)

    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책_한권_조회() {
        //given (데이터 준비)
        String title = "junit";
        String author = "chan";

        //when (테스트 실행)
        Book bookPS = bookRepository.findById(1L).get();

        //then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // transaction close --> rollback(init saved data)

    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책_한권_삭제() {
        //given (데이터 준비)
        Long id = 1L;

        //when (테스트 실행)
        bookRepository.deleteById(id);

        //then (검증)
        assertFalse(bookRepository.findById(id).isPresent());
    } // transaction close --> rollback(init saved data)
}
