package com.vincenzoiurilli.Ecommerce.repositories;

import com.vincenzoiurilli.Ecommerce.dto.products.ProductsStatisticDTO;
import com.vincenzoiurilli.Ecommerce.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import com.vincenzoiurilli.Ecommerce.entities.OrderProducts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, UUID> {

    @Query("SELECT COUNT(o) FROM OrderProducts o WHERE o.product.id = :productId")
    Integer countByProduct(@Param("productId") UUID productId);

    @Query("SELECT p.id, p.name, SUM(o.quantity), SUM(o.quantity * o.price_at_purchase) FROM OrderProducts o JOIN o.product p WHERE p.seller.id = :sellerId GROUP BY p.id, p.name")
    List<ProductsStatisticDTO> getProductsStatisticBySellerId(@Param("sellerId") UUID sellerId);

}
