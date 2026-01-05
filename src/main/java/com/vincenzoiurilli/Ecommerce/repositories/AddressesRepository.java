package com.vincenzoiurilli.Ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vincenzoiurilli.Ecommerce.entities.Addresses;
import java.util.UUID;

@Repository
public interface AddressesRepository extends JpaRepository<Addresses, UUID> {

    @Query("SELECT a FROM Addresses a WHERE a.address = :address AND a.city = :city AND a.region = :region AND a.state = :state")
    Addresses findAddress(@Param("address") String address, @Param("city") String city, @Param("region") String region, @Param("state") String state);

}
