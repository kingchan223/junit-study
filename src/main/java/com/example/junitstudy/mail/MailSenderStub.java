package com.example.junitstudy.mail;

import org.springframework.stereotype.Component;

//가짜
@Component
public class MailSenderStub implements MailSender{
    @Override
    public boolean send() {
        return true;
    }
}
