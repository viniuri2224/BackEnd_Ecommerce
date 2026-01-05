package com.vincenzoiurilli.Ecommerce.dto.orders;

public record GetOrderItemsResponseDTO(String productName, String productDescription, int quantity, float price) {
}
