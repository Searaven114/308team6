package com.team6.ecommerce.mail;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class MailService {

    //konfigürasyon olmadan çalışmıyor. bkz. application.properties

    //private final JavaMailSender mailSender;


    public void sendDiscountNotificationMail(String[] recipients, String content){ }


   // public void sendInvoiceMail(String recipient, PDF)


}
