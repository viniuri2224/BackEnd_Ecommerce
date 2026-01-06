package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.entities.Addresses;
import com.vincenzoiurilli.Ecommerce.entities.UserAddresses;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.repositories.UserAddressesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAddressesService {

    @Autowired
    private UserAddressesRepository userAddressesRepository;

    public void newUserAddresses(Users currentUser, Addresses newAddress){

        UserAddresses userAddresses = new UserAddresses(currentUser, newAddress);

        this.userAddressesRepository.save(userAddresses);

    }

    public UserAddresses findUserAddressesByUserIdAndAddressId(UUID userId, UUID addressId){
        UserAddresses userAddress = this.userAddressesRepository.findUserAddressesByUserIdAndAddressId(userId, addressId);
        if(userAddress == null){
            throw new NotFoundException("No Address found for User");
        }
        return userAddress;
    }

    public void deleteAddressAssociation(UserAddresses userAddresses){
        UserAddresses foundAddress = this.userAddressesRepository.findById(userAddresses.getId()).orElseThrow(() -> new NotFoundException(userAddresses.getId()));
        this.userAddressesRepository.delete(foundAddress);
    }

}
