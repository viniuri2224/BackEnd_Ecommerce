package com.vincenzoiurilli.Ecommerce.repositories;

import com.vincenzoiurilli.Ecommerce.entities.OrderProducts;
import com.vincenzoiurilli.Ecommerce.entities.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, UUID> {

    @Query("SELECT pc FROM ProductCategories pc WHERE pc.category = :categoryId AND pc.product = :productId")
    ProductCategories getProductCategoriesByCategoryIdAndProductId(@Param("categoryId") UUID categoryId, @Param("productId") UUID productId);

}
