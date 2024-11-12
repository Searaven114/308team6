package com.team6.ecommerce.productmanager;


import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Log4j2
@AllArgsConstructor
@Service
public class ProductManagerService {

    private static ProductRepository productRepo;
    private static CategoryRepository categoryRepo;

//    The product managers shall add/remove products as well as product categories, and manage the stocks.

//    Everything related to stock shall be done by the product manager.

//    The product manager is also in the role of delivery department since it controls the stock.
//    This means, the product manager shall view the invoices, products to be delivered, and the corresponding addresses for delivery.
//      A delivery list has the following properties:
//          delivery ID,
//          customer ID,
//          product ID,
//          quantity,
//          total price,
//          delivery address,
//          and a field showing whether the delivery has been completed or not.

//    Last but not least, the product managers shall approve or disapprove the comments. (10%)


    @Secured({"ROLE_PRODUCTMANAGER"})
    public Set<Category> listAllCategories(){
        return null;
    }

    @Secured({"ROLE_PRODUCTMANAGER"})
    public void removeAllCategories(){
        categoryRepo.deleteAll();
    }

    @Secured({"ROLE_PRODUCTMANAGER"})
    public void addCategory(Category category){
        //productManagerService.addCategory(category);

    }

    @Secured({"ROLE_PRODUCTMANAGER"})
    public void removeCategory(String categoryId){
        if (categoryRepo.existsById(categoryId)){
            categoryRepo.deleteById(categoryId);

            // DIKKAT !
            // SILINECEK KATEGORIYE SAHIP PRODUCTLARA BAKILMASI LAZIM VE EGER HERHANGI BIR PRODUCT SADECE BU KATEGORIYE AIT ISE O PRODUCTUN
            // INAKTIF EDILMESI GEREKIYOR. (şart değil gerçi)
        } else {
            //CategoryNotFoundException yaz
            throw new RuntimeException("Category not found");
        }
    }


//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    @Secured({"ROLE_PRODUCTMANAGER"})
    public List<?> listInvoices (){
        return null;
    }


    @Secured({"ROLE_PRODUCTMANAGER"})
    public List<?> listInvoiceByCustomer( String customerId){
        return null;
    }



//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    //Patchmapping ile kullanılacak herhalde idk
    //public void approveComment()

    @Secured({"ROLE_PRODUCTMANAGER"})
    public List<?> listUnapprovedComments(){
        return null;
    }

    @Secured({"ROLE_PRODUCTMANAGER"})
    public List<?> listAllComments(){
        return null;
    }

    @Secured({"ROLE_PRODUCTMANAGER"})
    public List<?> listCommentsByUser( String userId ){
        return null;
    }

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    @Secured({"ROLE_PRODUCTMANAGER"})
    public void addProduct(ProductDTO dto){

    }

    @Secured({"ROLE_PRODUCTMANAGER"})
    public void removeProduct(String productId){

    }

    @Secured({"ROLE_PRODUCTMANAGER"})
    public Product updateProductStock(String productId, int quantity) {

        //TODO buna ProductNotFound Exceptionu yaz
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setQuantityInStock(quantity);

        return productRepo.save(product);
    }






}

