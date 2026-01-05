package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name="Addresses")
public class Addresses {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false, unique = true)
    private String city;

    @Column(nullable = false, unique = true)
    private String region;

    @Column(nullable = false, unique = true)
    private String state;

    public Addresses() {}

    public Addresses(String address, String city, String region, String state) {
        this.address = address;
        this.city = city;
        this.region = region;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
