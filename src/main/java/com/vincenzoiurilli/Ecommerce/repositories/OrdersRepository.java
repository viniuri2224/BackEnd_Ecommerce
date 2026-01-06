package com.vincenzoiurilli.Ecommerce.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vincenzoiurilli.Ecommerce.entities.Orders;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, UUID>{

    @Query("SELECT o FROM Orders o WHERE o.user.id = :userId")
    List<Orders> getOrders(@Param("userId") UUID userId);

}
