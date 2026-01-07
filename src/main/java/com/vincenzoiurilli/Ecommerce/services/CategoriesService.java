package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.categories.NewCategoryDTO;
import com.vincenzoiurilli.Ecommerce.dto.categories.NewCategoryResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.Categories;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public NewCategoryResponseDTO createCategory(NewCategoryDTO body){
         Categories category = new Categories(body.name(), body.description(), body.type());

         Categories savedCategory = categoriesRepository.save(category);

         return new NewCategoryResponseDTO(savedCategory.getId());
    }

    public NewCategoryDTO findById(UUID categoryId){
        Categories category = categoriesRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId));

        return new NewCategoryDTO(category.getName(), category.getDescription(), category.getType());
    }

    public Categories findCategoryById(UUID categoryId){
         return categoriesRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId));
    }

    public List<NewCategoryDTO> findAll(){
        List<Categories> categories = categoriesRepository.findAll();
        List<NewCategoryDTO> categoriesDTO = new ArrayList<>();
        for (Categories category : categories) {
            categoriesDTO.add(new NewCategoryDTO(category.getName(), category.getDescription(), category.getType()));
        }
        return categoriesDTO;
    }

    public NewCategoryDTO updateCategory(UUID categoryId, NewCategoryDTO body){
        Categories category = this.categoriesRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId));

        category.setName(body.name());
        category.setDescription(body.description());
        category.setType(body.type());

        this.categoriesRepository.save(category);

        return new NewCategoryDTO(category.getName(), category.getDescription(), category.getType());
    }

    public void deleteCategory(UUID categoryId){
        Categories category = this.categoriesRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId));
        categoriesRepository.delete(category);
    }

}
