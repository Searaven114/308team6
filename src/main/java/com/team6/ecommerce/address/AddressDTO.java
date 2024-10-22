package com.team6.ecommerce.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//Bunun ülke/şehir/mahalle olayı frontendde dropdown ile yapılmalı, illa hazır kodu vardır
//öbür türlü "Torkiye" veya "Türkiye Cumhuriyeti" inputu ile "TR" inputunu eşleştirecek algoritma yazman lazım...

@Data
public class AddressDTO {

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String country;

    private String notes;

}
