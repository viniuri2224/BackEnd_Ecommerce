package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.categories.NewProductCategoryRespondeDTO;
import com.vincenzoiurilli.Ecommerce.entities.Categories;
import com.vincenzoiurilli.Ecommerce.entities.ProductCategories;
import com.vincenzoiurilli.Ecommerce.entities.Products;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.exceptions.UnauthorizedException;
import com.vincenzoiurilli.Ecommerce.repositories.ProductCategoriesRepository;
import com.vincenzoiurilli.Ecommerce.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCategoriesService {

    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CategoriesService categoriesService;


    public NewProductCategoryRespondeDTO associateProductToCategory(UUID productId, UUID categoryId, Users currentUser) {

        if(!(this.productsService.belongsToSeller(productId, currentUser))){
            throw new UnauthorizedException("You are not allowed to access this product");
        }

        Categories category = this.categoriesService.findCategoryById(categoryId);

        Products product = this.productsService.findById(productId);

        ProductCategories productCategory = new ProductCategories(category, product);

        ProductCategories savedProductCategory = this.productCategoriesRepository.save(productCategory);

        return new NewProductCategoryRespondeDTO(savedProductCategory.getId());

    }

    public void deleteAssociation(UUID productId, UUID categoryId, Users currentUser) {

        if(!(this.productsService.belongsToSeller(productId, currentUser))){
            throw new UnauthorizedException("You are not allowed to access this product");
        }

        ProductCategories productCategory = this.productCategoriesRepository.getProductCategoriesByCategoryIdAndProductId(categoryId, productId);

        if(productCategory == null){
            throw new NotFoundException("Association with product and category not found");
        }

        this.productCategoriesRepository.delete(productCategory);
    }

}
