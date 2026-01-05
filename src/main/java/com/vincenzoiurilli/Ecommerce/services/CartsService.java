package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.entities.Carts;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.repositories.CartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartsService {
    @Autowired
    private CartsRepository cartsRepository;

    public void newCarts(Users currentUser){
        Carts carts = new Carts(currentUser);

        this.cartsRepository.save(carts);
    }
}
