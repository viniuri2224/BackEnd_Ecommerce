package com.vincenzoiurilli.Ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vincenzoiurilli.Ecommerce.entities.UserAddresses;
import java.util.UUID;


@Repository
public interface UserAddressesRepository extends JpaRepository<UserAddresses, UUID> {

    @Query("SELECT ua FROM UserAddresses ua WHERE ua.user.id = :userId AND ua.address.id = :addressId")
    UserAddresses findUserAddressesByUserIdAndAddressId(@Param("userId") UUID userId, @Param("addressId") UUID addressId);
}
