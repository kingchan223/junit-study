package com.example.junitstudy.domain;

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
        List<Book> booksPSs = bookRepository.findAll();

        //then (검증)
        assertEquals(title, booksPSs.get(0).getTitle());
        assertEquals(author, booksPSs.get(0).getAuthor());
        assertEquals(1, booksPSs.size());
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
        assertFalse(bookRepository.findById(id).isPresent());//assertFalse(false) 임을 증명
    } // transaction close --> rollback(init saved data)

    /*
    * 현재 각각의 테스트 실행 전에 @BeforeEach를 사용하여 초기 데이터를 넣어준다. 즉 @BeforeEach ~ 각각의 @Test 메서드들까지 트랜잭션이 이어진다.
    * 그런데 아래의 '책 한권 수정' 테스트를 보면 @Sql 어노테이션을 사용하여 테이블을 drop 하고 다시 create 한다.
    * 그런데도 아래의 수정 테스트는 bookRepository.save(changedBook) 메서드로 성공적인 update 문을 날린다.
    * 원래 같으면 테이블이 초기화되어 수정할 데이터가 없어야 하는데 그대로 남아있는 것이다.
    * junit의 @Test는 Rollback이 디폴트로 true이다.
    * 즉 @Test 메서드 실행(트랜잭션 시작) --> @Test 메서드 종료(트랜잭션 종료) --> Rollback
    * (테스트를 제외한 서버에서는 Runtime Exception이 발생한 경우에 Rollback이 된다.)
    * *rollback: 메모리에 있는 데이터 날림. commit: 메모리에 있는 데이터 스토리지에 반영
    * 1. @BeforeEach 실행 : 한건 save. (아직 트랜잭션 종료 ㄴㄴ라서 메모리에 있는 상태)
    * 2. @Test의 @Sql 실행 : HDD에 있는 테이블 drop 후 다시 생성
    * 3. @Test의 save 실행 : 근데 이미 메모리에 id =1 의 데이터 있으므로 그것을 update 해줌.
    * 4. @Test의 findAll 실행 :  현재 id = 1인 데이터 하나이므로 이거 찾아옴.
    * 5. @Test 메서드 종료  : 트랜잭션 종료. 모든 데이터 drop.
    *
    * */
    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책_한권_수정() {
        //given (데이터 준비)
        Long id = 1L;
        String title = "junit5";
        String author = "chan_lee";
        Book changedBook = Book.builder().id(id).title(title).author(author).build();

        //when (테스트 실행)
        Book bookPS = bookRepository.save(changedBook);
        bookRepository.findAll().forEach(System.out::println);

        //then (검증)
//        assertFalse(bookRepository.findById(id).isPresent());
    } // transaction close --> rollback(init saved data)
}
