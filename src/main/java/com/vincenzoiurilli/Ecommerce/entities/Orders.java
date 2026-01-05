package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="Orders")
public class Orders {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="order_date", nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;


    public Orders() {
    }

    public Orders(Users user, LocalDateTime orderDate) {
        this.user = user;
        this.orderDate = orderDate;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
