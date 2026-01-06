package com.vincenzoiurilli.Ecommerce.repositories;

import com.vincenzoiurilli.Ecommerce.entities.CartProducts;
import com.vincenzoiurilli.Ecommerce.entities.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, UUID> {

    @Query("SELECT cp FROM CartProducts cp WHERE cp.product = :product AND cp.cart = :cart" )
    CartProducts getItem(@Param("product") UUID productId, @Param("cart") UUID cart);

    @Query("SELECT cp FROM CartProducts cp WHERE cp.cart = :cart")
    List<CartProducts> getCartItems(@Param("cart") UUID cart);


}
