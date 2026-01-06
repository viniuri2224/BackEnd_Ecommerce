package com.vincenzoiurilli.Ecommerce.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vincenzoiurilli.Ecommerce.entities.Products;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {
    @Query("SELECT p FROM Products p WHERE p.seller.id = :seller")
    List<Products> findBySeller(@Param("seller") UUID seller);

    @Query("SELECT p FROM Products p WHERE p.seller.id = :seller AND p.id = :productId")
    Products findyProductWithSeller(@Param("seller") UUID seller, @Param("productId") UUID productId);

}
