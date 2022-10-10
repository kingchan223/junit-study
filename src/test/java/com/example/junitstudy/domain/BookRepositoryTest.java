package com.example.junitstudy.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest /* --> DB와 관련된 컴포넌트만 메모리에 로딩 (즉 controller, service layer 는 메모리에 로딩 X) */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//DataJpaTest 는 in-memory db만 지원. 얘를 넣어줘야 외부 db도 사용가능
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void 책_등록() {
        System.out.println("책_등록 실행");

    }
}
