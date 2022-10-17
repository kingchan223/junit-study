package com.example.junitstudy.service;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.mail.MailSender;
import com.example.junitstudy.web.dto.BookRequest;
import com.example.junitstudy.web.dto.BookResponse;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1.책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponse 책_등록하기(BookRequest bookRequest) {
        Book bookPS = bookRepository.save(bookRequest.toEntity());
        // 책이 잘 저장되었다면 메일을 보낸다.
        if(!mailSender.send()){
            throw new RuntimeException("메일 전송에 실패했습니다.");//메일 전송에 실패했다면 익셉션을 날린다.
        }
        return BookResponse.toDto(bookPS);
    }

    // 2.책 목록 보기
    public List<BookResponse> 책_목록보기() {
        return bookRepository.findAll().stream().map(BookResponse::toDto).collect(Collectors.toList());
    }

    // 3.책 한건 보기
    public BookResponse 책_한건보기(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        return BookResponse.toDto(book);
    }

    // 4.책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책_삭제하기(@NotNull Long id) {
        bookRepository.deleteById(id);
    }

    // 5.책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책_수정하기(@NotNull Long id, BookRequest bookRequest) {
        Book bookPS = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        bookPS.update(bookRequest);
    }

}
