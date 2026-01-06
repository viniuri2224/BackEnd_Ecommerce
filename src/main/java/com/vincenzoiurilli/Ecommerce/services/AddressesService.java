package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.addresses.NewAddressDTO;
import com.vincenzoiurilli.Ecommerce.dto.addresses.NewAddressResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.Addresses;
import com.vincenzoiurilli.Ecommerce.entities.UserAddresses;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.exceptions.UnauthorizedException;
import com.vincenzoiurilli.Ecommerce.repositories.AddressesRepository;
import com.vincenzoiurilli.Ecommerce.repositories.UserAddressesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressesService {

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private UserAddressesService  userAddressesService;

    public boolean existAddress(String address, String city, String region, String state) {
        return this.addressesRepository.findAddress(address, city, region, state) != null;
    }

    public Addresses getAddress(String address, String city, String region, String state){
        return this.addressesRepository.findAddress(address, city, region, state);
    }

    public NewAddressResponseDTO newAddress(NewAddressDTO body, Users currentUser) {

        Addresses addresses = new Addresses(body.address().toUpperCase(), body.city().toUpperCase(), body.region().toUpperCase(), body.state().toUpperCase());
        Addresses savedAddress;

        if(!(existAddress(body.address().toUpperCase(), body.city().toUpperCase(), body.region().toUpperCase(), body.state().toUpperCase()))) {
             savedAddress = this.addressesRepository.save(addresses);
        }
        else {
             savedAddress = this.getAddress(body.address().toUpperCase(), body.city().toUpperCase(), body.region().toUpperCase(), body.state().toUpperCase());
        }

        this.userAddressesService.newUserAddresses(currentUser, savedAddress);

        return new NewAddressResponseDTO(savedAddress.getId());
    }

    public NewAddressDTO updateAddress(UUID addressId, NewAddressDTO body, Users currentUser) {

        UserAddresses userAddress = this.userAddressesService.findUserAddressesByUserIdAndAddressId(currentUser.getId(), addressId);

        if(userAddress == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        Addresses address = this.addressesRepository.findById(addressId).orElseThrow(() -> new NotFoundException(addressId));

        address.setAddress(body.address());
        address.setCity(body.city());
        address.setRegion(body.region());
        address.setState(body.state());
        this.addressesRepository.save(address);

        return new NewAddressDTO(address.getAddress(), address.getCity(), address.getRegion(), address.getState());

    }

    public void deleteAddress(UUID addressId, Users currentUser) {
        //In questo caso non vado ad eliminare l'inidirizzo, in quando altri utenti potrebbero avere lo stesso indirizzo (ad esempio diversi membri della famiglia, ma con account diversi)
        //Vado ad eliminare quindi solo l'associazione
        UserAddresses userAddress = this.userAddressesService.findUserAddressesByUserIdAndAddressId(currentUser.getId(), addressId);

        if(userAddress == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        this.userAddressesService.deleteAddressAssociation(userAddress);

    }

}
