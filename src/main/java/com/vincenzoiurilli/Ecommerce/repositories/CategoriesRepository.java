package com.vincenzoiurilli.Ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vincenzoiurilli.Ecommerce.entities.Categories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, UUID> {
}
