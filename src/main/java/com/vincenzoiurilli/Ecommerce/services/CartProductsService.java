package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.carts.NewCartProductDTO;
import com.vincenzoiurilli.Ecommerce.dto.carts.NewCartProductQtyDTO;
import com.vincenzoiurilli.Ecommerce.dto.carts.NewCartProductResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.Carts;
import com.vincenzoiurilli.Ecommerce.entities.Products;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.entities.CartProducts;
import com.vincenzoiurilli.Ecommerce.repositories.CartProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartProductsService {
    @Autowired
    private CartProductsRepository cartProductsRepository;

    @Autowired
    private ProductsService productsService;

    public NewCartProductResponseDTO addNewItemToCart(NewCartProductDTO body, Users currentUser) {
        Products product = this.productsService.findById(body.productId());
        Carts cart = currentUser.getCart();

        CartProducts item = new CartProducts(cart, product, body.quantity(), product.getPrice());

        CartProducts savedItem = this.cartProductsRepository.save(item);

        return new NewCartProductResponseDTO(savedItem.getId());

    }

    public NewCartProductDTO updateItemQuantity(UUID productId, NewCartProductQtyDTO body, Users currentUser) {
        Products product = this.productsService.findById(productId);
        Carts cart = currentUser.getCart();

        CartProducts item = this.cartProductsRepository.getItem(productId, cart.getId());
        
        item.setProductQuantity(body.quantity());

        this.cartProductsRepository.save(item);

        return new NewCartProductDTO(item.getProduct().getId(),item.getCart().getId(), item.getProductQuantity());

    }

    public void deleteItemFromCart(UUID productId, UUID cartId, Users currentUser) {
        Products product = this.productsService.findById(productId);
        Carts cart = currentUser.getCart();

        CartProducts item = this.cartProductsRepository.getItem(productId, cart.getId());

        this.cartProductsRepository.delete(item);
    }
}
