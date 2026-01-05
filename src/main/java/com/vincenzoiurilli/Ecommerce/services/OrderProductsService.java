package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.products.ProductsStatisticDTO;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.repositories.OrderProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderProductsService {
    @Autowired
    private OrderProductsRepository orderProductsRepository;

    public Integer countByProduct(UUID productId){
        return this.orderProductsRepository.countByProduct(productId);
    }

    public List<ProductsStatisticDTO> getProductsStatisticBySellerId(Users currentUser){
        return this.orderProductsRepository.getProductsStatisticBySellerId(currentUser.getId());
    }
}
