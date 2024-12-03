package com.team6.ecommerce.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

//Bunun ülke/şehir/mahalle olayı frontendde dropdown ile yapılmalı, illa hazır kodu vardır
//öbür türlü "Torkiye" veya "Türkiye Cumhuriyeti" inputu ile "TR" inputunu eşleştirecek algoritma yazman lazım...

@Data
@Builder
public class AddressDTO {

    private String street;

    private String city;

    private String zipCode;

    private String country;

    private String notes;

}
