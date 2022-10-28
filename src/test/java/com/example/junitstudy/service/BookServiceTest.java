package com.example.junitstudy.service;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.mail.MailSender;
import com.example.junitstudy.web.dto.BookListResponse;
import com.example.junitstudy.web.dto.BookRequest;
import com.example.junitstudy.web.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
    void 책_등록하기(){
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
    }

    @Test
    void 책_목록보기(){
        // 1.given

        // 2.stub (가설 정의)
        List<Book> books = Arrays.asList(
                new Book("junit1", "lee1"),
                new Book("junit2", "lee2")
        );
        when(bookRepository.findAll()).thenReturn(books);

        // 3.when
        BookListResponse 책_목록보기 = bookService.책_목록보기();
        List<BookResponse> result = 책_목록보기.getItems();
        // 4.then
        assertThat(result.size()).isEqualTo(2L);
        assertThat(result.get(0).getTitle()).isEqualTo("junit1");
        assertThat(result.get(0).getAuthor()).isEqualTo("lee1");
        assertThat(result.get(1).getTitle()).isEqualTo("junit2");
        assertThat(result.get(1).getAuthor()).isEqualTo("lee2");
    }

    @Test
    void 책_한건보기(){
        // 1.given
        Book book = new Book(1L, "junit", "Lee");
        // 2.stub (가설 정의)
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // 3.when
        BookResponse result = bookService.책_한건보기(1L);

        // 4.then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("junit");
        assertThat(result.getAuthor()).isEqualTo("Lee");
    }

    @Test
    void 책_삭제하기(){
        // 책 삭제는 repository의 delete 메서드를 호출할 뿐이라 할 필요 없음
    }

    @Test
    void 책_수정하기(){
        // 1.given
        Book book = new Book(1L, "junit", "Lee");
        BookRequest bookEditRequest = new BookRequest("junit2", "Kim");
        // 2.stub (가설 정의)
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // 3.when
        bookService.책_수정하기(1L, bookEditRequest);

        // 4.then
        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo(bookEditRequest.getTitle());
        assertThat(book.getAuthor()).isEqualTo(bookEditRequest.getAuthor());
    }
}
