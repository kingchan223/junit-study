package com.example.junitstudy.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest /* --> DB와 관련된 컴포넌트만 메모리에 로딩 (즉 controller, service layer 는 메모리에 로딩 X) */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//DataJpaTest 는 in-memory db만 지원. 얘를 넣어줘야 외부 db도 사용가능
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void 책_등록() {
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
    }
}
