package com.team6.ecommerce.notification;


import com.team6.ecommerce.invoice.Invoice;
import com.team6.ecommerce.invoice.InvoiceService;
import com.team6.ecommerce.mail.MailService;
import com.team6.ecommerce.product.Product;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class NotificationService {

    private final InvoiceService invoiceService;
    private final MailService mailService;


    // elimizde bir alıcı listesi olması lazım String[] tarzında, o alıcı listesi de "Wishlist" inde indirim/fiyat degisimi yapılan ürünlerden olan customerlerden oluşacak. yani fiyat degisimi yapıldıgıda sanırım
    // butun wishlistlerin traverse edilmesi gerekecek...

    public void notifyUsersAboutPriceChange(Product product) {
        //Mailservisi çağrılacak ! (mail.MailService)
        // Bunun kendisi ise Salesmanager'in price change metodunda çağrılacak yani delegation var
        // (salesmanagercontroller -> productService ->  notificationservice -> emailservice)
    }

    public void notifyUsersAboutDiscount(Product product, int discountRate) {
        //Mailservisi çağrılacak ! (mail.MailService)
    }

    public void notifyUserWithInvoice(Invoice invoice) {
        byte[] pdfContent = invoiceService.generateInvoicePDF(invoice);
        mailService.sendInvoiceMail(invoice.getEmail(), pdfContent, "Invoice_" + invoice.getId() + ".pdf");
    }
}