package com.vincenzoiurilli.Ecommerce.controllers;

import com.vincenzoiurilli.Ecommerce.dto.addresses.NewAddressDTO;
import com.vincenzoiurilli.Ecommerce.dto.addresses.NewAddressResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.carts.*;
import com.vincenzoiurilli.Ecommerce.dto.users.GetUsersResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.users.NewUserDTO;
import com.vincenzoiurilli.Ecommerce.dto.users.UpdatedUserResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.exceptions.ValidationException;
import com.vincenzoiurilli.Ecommerce.services.AddressesService;
import com.vincenzoiurilli.Ecommerce.services.CartProductsService;
import com.vincenzoiurilli.Ecommerce.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private AddressesService addressesService;

    @Autowired
    private CartProductsService cartProductsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<GetUsersResponseDTO> getUsers() {
        return this.usersService.getUsers();
    }

    @GetMapping("/me")
    public GetUsersResponseDTO getUser(@AuthenticationPrincipal Users user) {
        return this.usersService.findUserById(user.getId());
    }


    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    @PutMapping("/{userId}")
    public UpdatedUserResponseDTO getUserByIdAndUpdate(@PathVariable("userId") UUID userId, @RequestBody @Validated NewUserDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.usersService.findByIdAndUpdateUser(userId, body);
    }

    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable("userId") UUID userId) {
        this.usersService.findByIdAndDeleteUser(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    @PatchMapping("/{userId}/profile-picture")
    public String uploadImage(@RequestParam("image") MultipartFile file, @PathVariable("userId") UUID userId) {
        return this.usersService.uploadProfilePicture(file, userId);
    }

    @PostMapping("/addresses")
    public NewAddressResponseDTO createAddress(@RequestBody @Validated NewAddressDTO body, BindingResult validationResult, @AuthenticationPrincipal Users user){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.addressesService.newAddress(body,  user);
    }

    @PutMapping("/addresses/{addressId}")
    public NewAddressDTO updateAddress(@RequestBody @Validated NewAddressDTO body, BindingResult validationResult, @PathVariable("addressId") UUID addressId, @AuthenticationPrincipal Users user){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.addressesService.updateAddress(addressId, body, user);
    }

    @DeleteMapping("/addresses/{addressId}")
    public void deleteAddress(@PathVariable("addressId") UUID addressId, @AuthenticationPrincipal Users user) {
        this.addressesService.deleteAddress(addressId, user);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/me/carts")
    public NewCartProductResponseDTO addToCart(@RequestBody @Validated NewCartProductDTO body, BindingResult validationResult, @AuthenticationPrincipal Users user){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.cartProductsService.addNewItemToCart(body, user);
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping("/me/carts/items/{productId}")
    public NewCartProductDTO changeItemQty(@PathVariable("productId") UUID productId, @RequestBody @Validated NewCartProductQtyDTO body, BindingResult validationResult,@AuthenticationPrincipal Users user){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.cartProductsService.updateItemQuantity(productId, body, user);
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @DeleteMapping("/me/carts/items/{productId}")
    public void deleteItemFromCart(@PathVariable("productId") UUID productId, @AuthenticationPrincipal Users currentUser) {
        this.cartProductsService.deleteItemFromCart(productId, currentUser);
    }

    @GetMapping("/me/carts/items")
    public List<GetCartProductsDTO> getCartItems(@AuthenticationPrincipal Users currentUser) {
        return this.cartProductsService.getCartProducts(currentUser);
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/me/carts")
    public CartResponseDTO getCartId(@AuthenticationPrincipal Users currentUser) {
        return new CartResponseDTO(this.usersService.getCartId(currentUser.getId()));
    }


}
