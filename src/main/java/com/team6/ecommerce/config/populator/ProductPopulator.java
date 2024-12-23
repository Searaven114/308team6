package com.team6.ecommerce.config.populator;

//import com.borau.cs308demo.cart.CartRepository;


import com.github.javafaker.Faker;
import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Component
@DependsOn({"categoryPopulator", "distributorPopulator"})
public class ProductPopulator {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final ProductRepository productRepo;
    private final DistributorRepository distributorRepo;
    private final CategoryRepository categoryRepo;
    //private final CartRepository cartRepo;

    private final Faker fake = new Faker();


    @PostConstruct
    public void init() {


        productRepo.deleteAll();

        log.info("[ProductPopulator] Clearing Product Collection.");

        Optional<Category> monitorCat = categoryRepo.findById("2");
        Optional<Category> laptopCat = categoryRepo.findById("1");
        Optional<Category> mouseCat = categoryRepo.findById("4");

        List<Product> products = new ArrayList<>();

        monitorCat.ifPresent(category -> products.add(new Product(
                "1",
                "Dell UltraSharp Monitor",
                monitorCat.get().getId(),  // Retrieve the Category from Optional
                "Dell",
                "U2720Q",
                "D273928Q",
                "27-inch 4K monitor with vibrant colors and sharp details, ideal for productivity and media consumption.",
                35,
                1000.0,
                true,
                "1",
                20
        )));


        monitorCat.ifPresent(category -> products.add(new Product(
                "2",
                "Alienware AW2723DF",
                monitorCat.get().getId(),
                "Alienware",
                "AW2723DF",
                "AW2723DF123",
                "27-inch QHD monitor with a 360Hz refresh rate, QD-OLED technology and 0.03ms response time.",
                0,
                749.99,
                true,
                "2",
                2
        )));


        laptopCat.ifPresent(category -> products.add(new Product(
                "3",
                "HP Envy Laptop",
                laptopCat.get().getId(),
                "HP",
                "Envy 13",
                "HP3438739E",
                "Slim and powerful 13-inch laptop, with Intel Core i7 processor, 16GB RAM, and 512GB SSD.",
                25,
                1099.99,
                true,
                "2",
                7
        )));

        mouseCat.ifPresent(category -> products.add(new Product(
                "4",
                "Logitech MX Master 3",
                mouseCat.get().getId(),
                "Logitech",
                "MX Master 3",
                "LOGMXM331",
                "Ergonomic wireless mouse with programmable buttons, ideal for productivity and creative workflows.",
                0,
                20.0,
                true,
                "1",
                10
        )));

        laptopCat.ifPresent(category -> {
            products.add(new Product(
                    "5",
                    "MacBook Pro 16-inch",
                    laptopCat.get().getId(),
                    "Apple",
                    "M1 Pro",
                    "MBP16M1P2022",
                    "Apple MacBook Pro with M1 Pro chip, 16-inch Retina Display, 16GB RAM, 512GB SSD.",
                    0,
                    2499.99,
                    true,
                    "1",
                    70
            ));
        });

        laptopCat.ifPresent(category1 -> {
            products.add(new Product(
                    "6",
                    "Dell XPS 13",
                    laptopCat.get().getId(),
                    "Dell",
                    "XPS 13",
                    "XPS13-9300",
                    "Dell XPS 13 with Intel Core i7, 16GB RAM, 512GB SSD, and 13-inch 4K display.",
                    30,
                    1499.99,
                    true,
                    "2",
                    15
            ));
        });

        monitorCat.ifPresent(category -> {
            products.add(new Product(
                    "7",
                    "Samsung Odyssey G7",
                    monitorCat.get().getId(),
                    "Samsung",
                    "Odyssey G7",
                    "SOG7C32",
                    "32-inch curved gaming monitor with QHD resolution and 240Hz refresh rate.",
                    25,
                    699.99,
                    true,
                    "2",

                    60
            ));
        });

        monitorCat.ifPresent(category -> {
            products.add(new Product(
                    "8",
                    "LG UltraFine 5K",
                    monitorCat.get().getId(),
                    "LG",
                    "UltraFine 5K",
                    "LGUF5K",
                    "27-inch 5K display with Thunderbolt 3 for Mac users, perfect for creative professionals.",
                    20,
                    1299.99,
                    true,
                    "2",
                    15
            ));
        });


        mouseCat.ifPresent(category1 -> {
            products.add(new Product(
                    "9",
                    "Razer DeathAdder V2",
                    mouseCat.get().getId(),
                    "Razer",
                    "DeathAdder V2",
                    "RDAV2",
                    "Ergonomic gaming mouse with 20,000 DPI optical sensor and Razer Chroma RGB.",
                    50,
                    59.99,
                    true,
                    "1",
                    12
            ));
        });


        mouseCat.ifPresent(category -> {

            products.add(new Product(
                    "10",
                    "Corsair Dark Core RGB Pro",
                    mouseCat.get().getId(),
                    "Corsair",
                    "Dark Core RGB Pro",
                    "CDCRGBPRO",
                    "Wireless gaming mouse with customizable buttons, 18,000 DPI, and RGB lighting.",
                    45,
                    89.99,
                    true,
                    "1",
                    17
            ));
        });

        laptopCat.ifPresent(category -> products.add(new Product(
                "11",
                "Lenovo ThinkPad X1 Carbon",
                laptopCat.get().getId(),
                "Lenovo",
                "ThinkPad X1 Carbon",
                "TPX1C1234",
                "14-inch business laptop with Intel Core i7, 16GB RAM, 512GB SSD, known for its durability and performance.",
                40,
                1400.0,
                true,
                "2",
                19
        )));

        monitorCat.ifPresent(category -> products.add(new Product(
                "12",
                "Acer Predator X34",
                monitorCat.get().getId(),
                "Acer",
                "Predator X34",
                "ACPRDX34",
                "34-inch curved ultra-wide monitor with G-Sync support and 100Hz refresh rate, designed for gamers.",
                15,
                1000.0,
                true,
                "1",
                38
        )));

        mouseCat.ifPresent(category -> products.add(new Product(
                "13",
                "SteelSeries Rival 600",
                mouseCat.get().getId(),
                "SteelSeries",
                "Rival 600",
                "SSR600",
                "Dual sensor system gaming mouse with customizable weights, 12,000 DPI, and RGB lighting.",
                60,
                79.99,
                true,
                "2",
                21
        )));

        laptopCat.ifPresent(category -> products.add(new Product(
                "14",
                "Asus ROG Zephyrus G14",
                laptopCat.get().getId(),
                "Asus",
                "ROG Zephyrus G14",
                "ASROG14",
                "14-inch gaming laptop with AMD Ryzen 9, 16GB RAM, 1TB SSD, and RTX 3060 graphics card.",
                1,
                1599.99,
                true,
                "1",
                31
        )));

        monitorCat.ifPresent(category -> products.add(new Product(
                "15",
                "BenQ EX3501R",
                monitorCat.get().getId(),
                "BenQ",
                "EX3501R",
                "BEX35R",
                "35-inch curved monitor with HDR, 100Hz refresh rate, and ultra-wide QHD resolution.",
                10,
                849.99,
                true,
                "2",
                51
        )));

        mouseCat.ifPresent(category -> products.add(new Product(
                "16",
                "HyperX Pulsefire FPS Pro",
                mouseCat.get().getId(),
                "HyperX",
                "Pulsefire FPS Pro",
                "HPXFPSPRO",
                "RGB gaming mouse with 16,000 DPI, designed for FPS gamers, and equipped with Pixart 3389 sensor.",
                1,
                49.99,
                true,
                "2",
                12
        )));

        laptopCat.ifPresent(category -> products.add(new Product(
                "17",
                "Microsoft Surface Laptop 4",
                laptopCat.get().getId(),
                "Microsoft",
                "Surface Laptop 4",
                "MSLP4",
                "13.5-inch laptop with Intel Core i5, 8GB RAM, 256GB SSD, sleek design and excellent build quality.",
                30,
                999.99,
                true,
                "2",
                57
        )));

        monitorCat.ifPresent(category -> products.add(new Product(
                "18",
                "Gigabyte Aorus FI27Q",
                monitorCat.get().getId(),
                "Gigabyte",
                "Aorus FI27Q",
                "GA27FQ",
                "27-inch gaming monitor with 165Hz refresh rate, QHD resolution, and FreeSync support.",
                25,
                699.99,
                true,
                "1",
                19
        )));

        mouseCat.ifPresent(category -> products.add(new Product(
                "19",
                "Cooler Master MM710",
                mouseCat.get().getId(),
                "Cooler Master",
                "MM710",
                "CMM710",
                "Ultra-lightweight gaming mouse with honeycomb design, 16,000 DPI sensor, and RGB lighting.",
                50,
                49.99,
                true,
                "1",
                87
        )));

        laptopCat.ifPresent(category -> products.add(new Product(
                "20",
                "Acer Swift 3",
                laptopCat.get().getId(),
                "Acer",
                "Swift 3",
                "ASWIFT3",
                "14-inch ultrabook with AMD Ryzen 7, 8GB RAM, 512GB SSD, and up to 10 hours of battery life.",
                40,
                799.99,
                true,
                "2",
                43
        )));

        laptopCat.ifPresent(category -> products.add(new Product(
                "21",
                "HP Spectre x360",
                laptopCat.get().getId(),
                "HP",
                "Spectre x360",
                "HPSP360",
                "13-inch convertible laptop with Intel Core i7, 16GB RAM, 512GB SSD, and touchscreen.",
                20,
                1299.99,
                true,
                "1",
                37
        )));

        monitorCat.ifPresent(category -> products.add(new Product(
                "22",
                "ASUS ROG Swift PG259QN",
                monitorCat.get().getId(),
                "ASUS",
                "ROG Swift PG259QN",
                "ASRPG259",
                "24.5-inch gaming monitor with 360Hz refresh rate, designed for eSports and competitive gaming.",
                10,
                699.99,
                true,
                "2",
                65
        )));

        mouseCat.ifPresent(category -> products.add(new Product(
                "23",
                "Roccat Kone Pro Air",
                mouseCat.get().getId(),
                "Roccat",
                "Kone Pro Air",
                "RKPA123",
                "Wireless ergonomic gaming mouse with RGB lighting and Titan Switch Optical technology.",
                40,
                99.99,
                true,
                "1",
                59
        )));

        laptopCat.ifPresent(category -> products.add(new Product(
                "24",
                "Dell Inspiron 15 5000",
                laptopCat.get().getId(),
                "Dell",
                "Inspiron 15 5000",
                "DINSP155",
                "15.6-inch laptop with Intel Core i5, 8GB RAM, and 256GB SSD, perfect for daily use.",
                30,
                699.99,
                true,
                "2",
                29
        )));

        monitorCat.ifPresent(category -> products.add(new Product(
                "25",
                "MSI Optix MAG272C",
                monitorCat.get().getId(),
                "MSI",
                "Optix MAG272C",
                "MSOMAG27",
                "27-inch curved gaming monitor with 165Hz refresh rate, FHD resolution, and FreeSync support.",
                25,
                349.99,
                true,
                "1",
                19
        )));

        mouseCat.ifPresent(category -> products.add(new Product(
                "26",
                "Logitech G502 Lightspeed",
                mouseCat.get().getId(),
                "Logitech",
                "G502 Lightspeed",
                "LOGG502LS",
                "Wireless gaming mouse with 25,600 DPI HERO sensor, customizable weights, and RGB lighting.",
                45,
                139.99,
                true,
                "2",
                40
        )));

        laptopCat.ifPresent(category -> products.add(new Product(
                "27",
                "Apple MacBook Air M1",
                laptopCat.get().getId(),
                "Apple",
                "MacBook Air M1",
                "MBA13M1",
                "13-inch MacBook Air with Apple M1 chip, 8GB RAM, and 256GB SSD, known for its exceptional battery life.",
                35,
                999.99,
                true,
                "1",
                30
        )));

        monitorCat.ifPresent(category -> products.add(new Product(
                "28",
                "AOC Agon AG273QCG",
                monitorCat.get().getId(),
                "AOC",
                "Agon AG273QCG",
                "AOCAG273",
                "27-inch gaming monitor with 165Hz refresh rate, G-Sync support, and QHD resolution.",
                15,
                599.99,
                true,
                "2",
                5
        )));

        mouseCat.ifPresent(category -> products.add(new Product(
                "29",
                "Razer Naga Pro",
                mouseCat.get().getId(),
                "Razer",
                "Naga Pro",
                "RZNAGAPRO",
                "Wireless modular gaming mouse with swappable side plates and 20,000 DPI optical sensor.",
                50,
                149.99,
                true,
                "1",
                2
        )));

        laptopCat.ifPresent(category -> products.add(new Product(
                "30",
                "Asus VivoBook S14 Comment comment",
                laptopCat.get().getId(),
                "Asus",
                "VivoBook S14 Comment comment",
                "ASVIVO14",
                "14-inch lightweight laptop with Intel Core i5 comment, 8GB RAM, and 512GB SSD, designed for students and professionals.",
                25,
                749.99,
                true,
                "2",
                17
        )));

        productRepo.saveAll(products);

    }
}