package com.maid.cafe.c.maidcafec.Utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

//todo Set up this class. Check something like mailnator for setup
@Service
public class EmailUtils {
    private JavaMailSender  javaMailSender; //Todo maybe it must be autowired...

    public void sendSimpleMessage(String to, String subject, String message, List<String> list){
        SimpleMailMessage the_dying_message = new SimpleMailMessage();
        the_dying_message.setFrom("Akasaka"); // Todo set up mail system
        the_dying_message.setTo(to);
        the_dying_message.setSubject(subject);
        the_dying_message.setText(message);

        if (list != null && list.size() > 0) {
            the_dying_message.setCc(getCCArray(list));
        }

        javaMailSender.send(the_dying_message);
    }

    private String[] getCCArray(List<String> CCList){
        String[] cc = new String[CCList.size()];

        int i;
        for (i = 0; i < CCList.size(); i++){
            cc[i] = CCList.get(i);
        }

        return cc;
    }

    public void forgotMail(String to, String subject, String password) throws MessagingException{
        String htmlMessage = "//"; // Todo set up this thing

        MimeMessage the_dying_message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(the_dying_message, true);

        mimeMessageHelper.setFrom("Akasaka"); // Todo set up mail system
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        the_dying_message.setContent(htmlMessage, "text/html");

        javaMailSender.send(the_dying_message);
    }
}
