package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.carts.GetCartProductsDTO;
import com.vincenzoiurilli.Ecommerce.dto.carts.NewCartProductDTO;
import com.vincenzoiurilli.Ecommerce.dto.carts.NewCartProductQtyDTO;
import com.vincenzoiurilli.Ecommerce.dto.carts.NewCartProductResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.Carts;
import com.vincenzoiurilli.Ecommerce.entities.Products;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.entities.CartProducts;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.repositories.CartProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        CartProducts savedItem;
        CartProducts cartProductsFound = this.cartProductsRepository.getItem(product.getId(), cart.getId());
        if (cartProductsFound == null) {
            CartProducts item = new CartProducts(cart, product, body.quantity(), product.getPrice());

            savedItem = this.cartProductsRepository.save(item);
        }
        else{
            cartProductsFound.setProductQuantity(cartProductsFound.getProductQuantity() + body.quantity());
            savedItem = this.cartProductsRepository.save(cartProductsFound);
        }


        return new NewCartProductResponseDTO(savedItem.getId());

    }

    public NewCartProductDTO updateItemQuantity(UUID productId, NewCartProductQtyDTO body, Users currentUser) {
        Products product = this.productsService.findById(productId);
        Carts cart = currentUser.getCart();

        CartProducts item = this.cartProductsRepository.getItem(productId, cart.getId());

        if (item == null) {
            throw new NotFoundException("Product on cart not found");
        }

        item.setProductQuantity(body.quantity());

        this.cartProductsRepository.save(item);

        return new NewCartProductDTO(item.getProduct().getId(),item.getCart().getId(), item.getProductQuantity());

    }

    public void deleteItemFromCart(UUID productId, Users currentUser) {
        Products product = this.productsService.findById(productId);
        Carts cart = currentUser.getCart();

        CartProducts item = this.cartProductsRepository.getItem(productId, cart.getId());

        if (item == null) {
            throw new NotFoundException("Product on cart not found");
        }

        this.cartProductsRepository.delete(item);
    }

    public List<GetCartProductsDTO> getCartProducts(Users currentUser) {
        Carts cart = currentUser.getCart();

        List<CartProducts> cartItems = this.cartProductsRepository.getCartItems(cart.getId());

        if (cartItems.isEmpty()) {
            throw new NotFoundException("No item on cart found");
        }

        List<GetCartProductsDTO> dtos = new ArrayList<>();
        for (CartProducts item : cartItems) {
            GetCartProductsDTO dto = new GetCartProductsDTO(
                    item.getProduct().getId(),
                    item.getCart().getId(),
                    item.getProductQuantity(),
                    item.getProduct().getPrice()

            );

            dtos.add(dto);

        }
        return dtos;

    }

    public List<CartProducts> getCartItems(UUID cartId){
        List<CartProducts> items = this.cartProductsRepository.getCartItems(cartId);

        if (items.isEmpty()) {
            throw new NotFoundException(cartId);
        }

        return items;
    }

    public void deleteCartItemAfterOrder(CartProducts item){

        CartProducts foundItem = this.cartProductsRepository.findById(item.getId()).orElseThrow(() -> new NotFoundException(item.getId()));

        this.cartProductsRepository.delete(foundItem);
    }

}
