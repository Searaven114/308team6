package com.team6.ecommerce.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Document(collection = "products")
public class Product {

    @Id
    private String id; //6701927e78c5a70ef8d3a4e4

    @Indexed(unique = true)
    @NotBlank
    @Size(min = 3, message = "Product name must contain atleast 3 characters")
    private String title; //Asus VG259QM Monitor

    @Indexed(unique = true)
    @NotBlank
    private String categoryId; // Reference to the category ID instead of the Category object

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String serialNumber;

    private String description;
    private int quantityInStock;

    @Min(value = 0, message = "basePrice cannot be lower than 0")
    private double basePrice;

    private boolean warrantyStatus;
    private String distributorId;
    // List<String> commentIds ?

    // Popularity metric, updated based on purchases, buna algoritma bulmak lazım hesaplanması için.
    private double popularityPoint;

//    private List<String> images;




    // Constructor without id (used when creating new products)
    public Product(String name, String categoryId, String brand, String model, String serialNumber, String description, int quantityInStock, double basePrice, boolean warrantyStatus, String distributor) {
        this.title = name;
        this.categoryId = categoryId;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.basePrice = basePrice;
        this.warrantyStatus = warrantyStatus;
        this.distributorId = distributor;
        this.popularityPoint  = 0;
    }

    // Constructor with id (used when retrieving or updating existing products)
    public Product(String id, String name, String categoryId, String brand, String model, String serialNumber, String description, int quantityInStock, double basePrice, boolean warrantyStatus, String distributor) {
        this.id = id;
        this.title = name;
        this.categoryId = categoryId;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.basePrice = basePrice;
        this.warrantyStatus = warrantyStatus;
        this.distributorId = distributor;
    }
}
