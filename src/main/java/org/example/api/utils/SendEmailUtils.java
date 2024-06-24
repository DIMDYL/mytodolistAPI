package org.example.api.utils;

import lombok.Data;
import org.example.api.content.common.IdentifyingCodeMsg;
import org.example.api.exception.SendMailFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Data
public class SendEmailUtils {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from = null;
    public void sendSimpleEmail(String to,String title,String content){
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

    public void sendComplexEmail(String to,String title,String context) throws MessagingException {
//        创建消息对象
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

//        填入消息
        helper.setSubject(title);
        helper.setText(context);
        helper.setTo(to);
        helper.setFrom(from);

//        发送消息
        sender.send(mimeMessage);
    }
}
