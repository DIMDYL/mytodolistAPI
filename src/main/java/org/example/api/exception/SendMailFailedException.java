package org.example.api.exception;

import org.springframework.mail.MailException;

public class SendMailFailedException extends MailException {
    public SendMailFailedException(String msg) {
        super(msg);
    }
}
