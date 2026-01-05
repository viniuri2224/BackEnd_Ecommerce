package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="Categories")
public class Categories {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String description;
    @Column(unique = true)
    private String type;

    public Categories() {}

    public Categories(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
