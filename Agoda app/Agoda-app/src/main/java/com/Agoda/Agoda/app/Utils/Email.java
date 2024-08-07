package com.Agoda.Agoda.app.Utils;

import com.Agoda.Agoda.app.Payload.CreateAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Email {
    @Autowired
    private  JavaMailSender javaMailSender;

        public void sendEmail(String to,String sub,String msg) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toString());
            message.setSubject(sub);
            message.setText(msg);

            javaMailSender.send(message);
    }


}
