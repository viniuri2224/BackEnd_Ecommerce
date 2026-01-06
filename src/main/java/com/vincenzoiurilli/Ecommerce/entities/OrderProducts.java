package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name="OrderProducts")
public class OrderProducts {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float price_at_purchase;

    public OrderProducts() {}

    public OrderProducts(Orders order, Products product, int quantity, float price_at_purchase) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price_at_purchase = price_at_purchase;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice_at_purchase() {
        return price_at_purchase;
    }

    public void setPrice_at_purchase(float price_at_purchase) {
        this.price_at_purchase = price_at_purchase;
    }
}
