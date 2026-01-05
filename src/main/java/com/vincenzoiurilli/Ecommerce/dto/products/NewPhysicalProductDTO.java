package com.vincenzoiurilli.Ecommerce.dto.products;


public record NewPhysicalProductDTO(String name, String description, float price, int quantity, float weight, String dimensions, boolean shipping_required) {
}
