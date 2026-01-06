package com.vincenzoiurilli.Ecommerce.controllers;

import com.vincenzoiurilli.Ecommerce.dto.categories.NewCategoryDTO;
import com.vincenzoiurilli.Ecommerce.dto.categories.NewCategoryResponseDTO;
import com.vincenzoiurilli.Ecommerce.exceptions.ValidationException;
import com.vincenzoiurilli.Ecommerce.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{categoryId}")
    public NewCategoryDTO findById(@PathVariable("categoryId") UUID categoryId){
        return this.categoriesService.findById(categoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<NewCategoryDTO> findAll(){
        return this.categoriesService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public NewCategoryResponseDTO createCategory(@RequestBody @Validated NewCategoryDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return categoriesService.createCategory(body);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{categoryId}")
    public NewCategoryDTO updateCategory(@PathVariable("categoryId") UUID categoryId, @RequestBody @Validated NewCategoryDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.categoriesService.updateCategory(categoryId, body);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") UUID categoryId){
        this.categoriesService.deleteCategory(categoryId);
    }



}
