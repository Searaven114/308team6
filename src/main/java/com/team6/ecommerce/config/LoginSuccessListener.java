package com.team6.ecommerce.config;


import com.team6.ecommerce.user.User;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        Authentication auth = event.getAuthentication();
        User user = (User) auth.getPrincipal();

        log.info("[LoginSuccessListener] User {} is Successfully logged in.", user.getUsername());

        /*
         NOT: KULLANICI HER GİRİŞ YAPTIĞINDA, EĞER BİR CART'I VARSA O CARTI TARANACAK VE CARTINDAKİ ÜRÜNLERDEN HERHANGİ BİRİ SİTEDE
         TÜKENMİŞ YADA CARTTAKİ ADETİ SAĞLAYAMAMIŞ DURUMDA OLURSA O ÜRÜN CARTTAN SİLİNECEK VE KULLANICIYA BİR MESAJ YOLLANACAK
         "Due to stock changes, some items were removed from your cart" DİYE.
        */

        //Burada ayrıca cart merging logic'i de execute edilebilir.

    }
}
