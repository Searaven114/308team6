package com.team6.ecommerce.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "categories")
public class Category {

    private String id;
    private String name;
    private Boolean isActive;

    public Category(String name, Boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }
}

/*
PROBLEM:
    ya category'nin içinde bir list of product id's olacak

                            ya da

    product'un içinde categoryId olacak, ben 2.sini yaptım, duruma göre kararın incelenmesi lazım ileride
    eklenecek özellikler için sorun çıkarmaması için.
 */