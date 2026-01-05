package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name="ProductCategories")
public class ProductCategories {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Categories category;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Products product;

    public ProductCategories() {}

    public ProductCategories(Categories category, Products product) {
        this.category = category;
        this.product = product;
    }

    public UUID getId() {
        return id;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
