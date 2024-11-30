package com.team6.ecommerce.category;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;


    public List<Category> getAllActiveCategory() {
        List<Category> categories = categoryRepo.findByIsActiveTrue();
        return categories;
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();

    }

    public List<Category> findAllActive(){
        return categoryRepo.findByIsActiveTrue();
    }

    public Optional<Category> findById(String id) {
        return categoryRepo.findById(id);

    }

    public Category save(Category category) {
        return categoryRepo.save(category);

    }

    public void deactivateCategory(String categoryId){

        Optional<Category> target = categoryRepo.findById(categoryId);

        if (target.isPresent() ){
            if (target.get().getIsActive()){
                target.get().setIsActive(false);
            }
        }

        // Bu kategoriye bağlı ürünlerin ne olacağına karar verilmeli, ya ürünler o kategori altında gösterilmeyecek (sanırım bu olacak)
        // ya da o kategoriye bağlı bütün ürünlerin henüz olmayan "isActive" fieldi "false" ye çevirlecek.



    }


    // Tek bu metod ile hem deaktivasyon işlemi hemde isim değiştirme işlevi yapılabilir gibi
    public Category update(String id, Category categoryDetails) {
        return categoryRepo.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    category.setIsActive(categoryDetails.getIsActive());

                    return categoryRepo.save(category);
                })
                .orElseGet(() -> {
                    categoryDetails.setId(id);
                    return categoryRepo.save(categoryDetails);
                });
    }
}












