package com.vincenzoiurilli.Ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vincenzoiurilli.Ecommerce.entities.Carts;
import java.util.UUID;

@Repository
public interface CartsRepository extends JpaRepository<Carts, UUID> {
}
