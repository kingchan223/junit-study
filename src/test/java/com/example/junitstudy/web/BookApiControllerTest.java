package com.example.junitstudy.web;
import static org.junit.jupiter.api.Assertions.*;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.service.BookService;
import com.example.junitstudy.web.dto.BookRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

// 통합 테스트 (Controller, Service, Repository)
// 컨트롤러만 테스트 하는 거 아님
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate rt;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach// 모든 각 테스트 시작 전에 한번씩 실행
    void 데이터_준비() {
        String title = "junit";
        String author = "chan";
        Book book = Book.builder()
                .title(title)
                .author(author).build();

        bookRepository.save(book);
    }

    @DisplayName("통합 - 책 한권 저장하기")
    @Sql("classpath:db/tableInit.sql")//이 테스트가 종료되면 table drop
    @Test
    void saveBook_test() throws JsonProcessingException {
        // given
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("이찬영");
        bookRequest.setTitle("junit 알기");
        String body = om.writeValueAsString(bookRequest);
//        System.out.println("body = " + body);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
//        System.out.println("response = " + response);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");
        assertThat(author).isEqualTo("이찬영");
        assertThat(title).isEqualTo("junit 알기");
    }

    @DisplayName("통합 - 책 리스트 조회하기")
    @Sql("classpath:db/tableInit.sql")//이 테스트가 종료되면 table drop
    @Test
    void getBookList() {
        //given

        //when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request, String.class);

        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
//        System.out.println("dc = " + dc.jsonString());
        int code = dc.read("$.code");
        String titleOfFirstItem = dc.read("$.body.items[0].title");
        String authorOfFirstItem = dc.read("$.body.items[0].author");

        // then
        assertThat(code).isEqualTo(1);
        assertThat(titleOfFirstItem).isEqualTo("junit");
        assertThat(authorOfFirstItem).isEqualTo("chan");
    }

    @DisplayName("통합 - 책 한권 id로 조회하기")
    @Sql("classpath:db/tableInit.sql")//이 테스트가 종료되면 table drop
    @Test
    void getBookOne(){

        //given
        Long id = 1L;

        //when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/"+id, HttpMethod.GET, request, String.class);
        DocumentContext dc = JsonPath.parse(response.getBody());
//        System.out.println("dc.jsonString = " + dc.jsonString());
        int code = dc.read("$.code");
        String titleOfItem = dc.read("$.body.title");
        String authorOfItem = dc.read("$.body.author");

        // then
        assertThat(code).isEqualTo(1);
        assertThat(titleOfItem).isEqualTo("junit");
        assertThat(authorOfItem).isEqualTo("chan");
     }

     @DisplayName("통합 - 책 한권 id로 삭제하기")
     @Sql("classpath:db/tableInit.sql")//이 테스트가 종료되면 table drop
     @Test
     void delete(){

         //given
         Long id = 1L;

         //when
         HttpEntity<String> request = new HttpEntity<>(null, headers);
         ResponseEntity<String> response = rt.exchange("/api/v1/book/"+id, HttpMethod.DELETE, request, String.class);
         DocumentContext dc = JsonPath.parse(response.getBody());
//         System.out.println("dc.jsonString = " + dc.jsonString());
         int code = dc.read("$.code");
         String msg = dc.read("$.msg");

         // then
         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

         assertThat(code).isEqualTo(1);
         assertThat(msg).isEqualTo("delete book");
      }

}
