package com.vincenzoiurilli.Ecommerce.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vincenzoiurilli.Ecommerce.entities.Orders;
import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, UUID>{
}
