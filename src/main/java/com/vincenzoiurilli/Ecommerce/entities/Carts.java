package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name="Carts")
public class Carts {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false, unique = true)
    private Users user;

    public Carts() {}

    public Carts(Users user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
