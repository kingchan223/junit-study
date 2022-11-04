package com.example.junitstudy.mail;

import org.springframework.stereotype.Component;

@Component
public class MailSenderAdaptor implements MailSender{

    private Mail mail;

    public MailSenderAdaptor() {
        this.mail = new Mail();
    }

    @Override
    public boolean send() {
        return mail.sendMail();
    }
}
