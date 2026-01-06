package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="PhysicalProduct")
public class PhysicalProduct extends Products{

    @Column(nullable = false)
    private float weight;
    @Column(nullable = false)
    private String dimensions;
    @Column(nullable = false)
    private boolean shipping_required;

    public PhysicalProduct() {}

    public PhysicalProduct(String name, String description, Users seller, float price, int quantity, float weight, String dimensions, boolean shipping_required) {
        super(name, description, seller, price, quantity);
        this.weight = weight;
        this.dimensions = dimensions;
        this.shipping_required = shipping_required;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public boolean isShipping_required() {
        return shipping_required;
    }

    public void setShipping_required(boolean shipping_required) {
        this.shipping_required = shipping_required;
    }

    @Override
    public String toString() {
        return "PhysicalProduct{" +
                "weight=" + weight +
                ", dimensions='" + dimensions + '\'' +
                ", shipping_required=" + shipping_required +
                '}';
    }
}
