package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name="CartProducts")
public class CartProducts {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable = false)
    private Carts cart;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private int productQuantity;

    @Column(nullable = false)
    private float productPrice;

    public CartProducts() {}

    public CartProducts(Carts cart, Products product, int productQuantity, float productPrice) {
        this.cart = cart;
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public UUID getId() {
        return id;
    }

    public Carts getCart() {
        return cart;
    }

    public void setCart(Carts cart) {
        this.cart = cart;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }
}
