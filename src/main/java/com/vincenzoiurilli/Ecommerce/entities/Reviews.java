package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="Reviews")
public class Reviews {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int stars;


    private String description;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Users customer_id;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Products product_id;

    private LocalDateTime date_of_issue;

    public Reviews() {}

    public Reviews(int stars, String description, Users customer_id, Products product_id, LocalDateTime date_of_issue) {
        this.stars = stars;
        this.description = description;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.date_of_issue = date_of_issue;
    }

    public UUID getId() {
        return id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Users getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Users customer_id) {
        this.customer_id = customer_id;
    }

    public Products getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Products product_id) {
        this.product_id = product_id;
    }

    public LocalDateTime getDate_of_issue() {
        return date_of_issue;
    }

    public void setDate_of_issue(LocalDateTime date_of_issue) {
        this.date_of_issue = date_of_issue;
    }
}
