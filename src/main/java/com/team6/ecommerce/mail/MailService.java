package com.team6.ecommerce.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class MailService {

    private JavaMailSender mailSender;

    public void sendDiscountNotificationMail(String[] recipients, String content){ }


    public void sendInvoiceMail(String recipient, byte[] pdfContent, String fileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipient);
            helper.setSubject("Your Invoice");
            helper.setText("Please find your invoice attached.");
            helper.addAttachment(fileName, new ByteArrayDataSource(pdfContent, "application/pdf"));

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


}
