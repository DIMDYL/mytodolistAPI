package org.example.api.utils;

import lombok.Data;
import org.example.api.content.IdentifyingCodeMsg;
import org.example.api.exception.SendMailFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Data
public class SendEmailUtils {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from = null;
    public void sendEmail(String to,String title,String content){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(content);
        try{
            sender.send(mailMessage);
        }catch (Exception e){
            throw new SendMailFailedException(IdentifyingCodeMsg.SEND_CHECKING_CODE_FAILED);
        }
    }

}
