package com.example.junitstudy.web;
import static org.junit.jupiter.api.Assertions.*;
import com.example.junitstudy.service.BookService;
import com.example.junitstudy.web.dto.BookRequest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

// 통합 테스트 (Controller, Service, Repository)
// 컨트롤러만 테스트 하는 거 아님
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private BookService bookService;

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

    @Test
    void saveBook_test() throws JsonProcessingException {

        // given
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("이찬영");
        bookRequest.setTitle("junit 알기");
        String body = om.writeValueAsString(bookRequest);
        System.out.println("body = " + body);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
        System.out.println("response = " + response);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        System.out.println("dc = " + dc);
        String s = dc.jsonString();
        System.out.println("s = " + s);
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(author).isEqualTo("이찬영");
        assertThat(title).isEqualTo("junit 알기");
    }
}
