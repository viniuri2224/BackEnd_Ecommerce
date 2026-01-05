package com.vincenzoiurilli.Ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.vincenzoiurilli.Ecommerce.entities.Reviews;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, UUID> {
}
