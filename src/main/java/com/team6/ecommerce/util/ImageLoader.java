package com.team6.ecommerce.util;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ImageLoader {

    // Directory where images are stored
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images/"; // Adjust path if necessary

    /**
     * Loads product images as a map of product IDs to image data.
     *
     * @return Map of product IDs to their respective image bytes.
     */
    public static Map<String, byte[]> loadProductImages() {
        Map<String, byte[]> images = new HashMap<>();

        try {
            images.put("1", Files.readAllBytes(Path.of(IMAGE_DIRECTORY + "dell_monitor.jpg")));
//            images.put("2", Files.readAllBytes(Path.of(IMAGE_DIRECTORY + "alienware_monitor.jpg")));
//            images.put("3", Files.readAllBytes(Path.of(IMAGE_DIRECTORY + "hp_envy_laptop.jpg")));
//            images.put("4", Files.readAllBytes(Path.of(IMAGE_DIRECTORY + "logitech_mx_master_3.jpg")));
//            images.put("5", Files.readAllBytes(Path.of(IMAGE_DIRECTORY + "macbook_pro.jpg")));
            // Add more mappings as needed for other products
        } catch (IOException e) {
            log.error("Error loading images: ", e);
        }

        return images;
    }
}

