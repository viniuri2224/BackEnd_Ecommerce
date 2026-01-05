package com.vincenzoiurilli.Ecommerce.controllers;

import com.vincenzoiurilli.Ecommerce.dto.categories.NewProductCategoryRespondeDTO;
import com.vincenzoiurilli.Ecommerce.dto.products.*;
import com.vincenzoiurilli.Ecommerce.entities.DigitalProduct;
import com.vincenzoiurilli.Ecommerce.entities.PhysicalProduct;
import com.vincenzoiurilli.Ecommerce.entities.Products;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.services.ProductCategoriesService;
import com.vincenzoiurilli.Ecommerce.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.PartHttpMessageWriter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductCategoriesService productCategoriesService;

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/digital-products")
    public NewProductResponseDTO createNewDigitalProduct(@AuthenticationPrincipal Users currentUser, @RequestBody NewDigitalProductDTO body){
        return productsService.createNewProduct(body, currentUser);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/physical-products")
    public NewProductResponseDTO createNewPhysicalProduct(@AuthenticationPrincipal Users currentUser, @RequestBody NewPhysicalProductDTO body){
        return productsService.createNewProduct(body, currentUser);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/digital-products/{productId}")
    public UpdatedDigitalProductResponseDTO getProductByIdAndUpdate(@PathVariable("productId") UUID productId, @RequestBody NewDigitalProductDTO body, @AuthenticationPrincipal Users currentUser){
        return this.productsService.findByIdAndUpdate(productId, body, currentUser);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/physical-products/{productId}")
    public UpdatedPhysicalProductResponseDTO getProductByIdAndUpdate(@PathVariable("productId") UUID productId, @RequestBody NewPhysicalProductDTO body, @AuthenticationPrincipal Users currentUser){
        return this.productsService.findByIdAndUpdate(productId, body, currentUser);
    }

    @GetMapping
    public List<Products> getProducts(@AuthenticationPrincipal Users currentUser){
        return this.productsService.getAllProducts(currentUser);
    }

    @GetMapping("/{productId}")
    public Products getProductById(@PathVariable("productId") UUID productId, @AuthenticationPrincipal Users currentUser){
        return this.productsService.findById(productId, currentUser);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable("productId") UUID productId, @AuthenticationPrincipal Users currentUser){
        this.productsService.findByIdAndDelete(productId, currentUser);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/statistics")
    public List<ProductsStatisticDTO> getProductsStatisticBySellerId(@AuthenticationPrincipal Users currentUser){
        return this.productsService.getProductsStatisticBySellerId(currentUser);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/{productId}/categories/{categoryId}")
    public NewProductCategoryRespondeDTO associateProductToCategory(@PathVariable("productId") UUID productId, @PathVariable("categoryId") UUID categoryId, @AuthenticationPrincipal Users currentUser){
        return this.productCategoriesService.associateProductToCategory(productId, categoryId, currentUser);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/{productId}/categories/{categoryId}")
    public void deleteAssociationProductCategory(@PathVariable("productId") UUID productId, @PathVariable("categoryId") UUID categoryId, @AuthenticationPrincipal Users currentUser){
        this.productCategoriesService.deleteAssociation(productId, categoryId, currentUser);
    }

}
