package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name="UserAddresses")
public class UserAddresses {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name="address_id", nullable = false)
    private Addresses address;

    public UserAddresses() {}

    public UserAddresses(Users user, Addresses address) {
        this.user = user;
        this.address = address;
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

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }
}
