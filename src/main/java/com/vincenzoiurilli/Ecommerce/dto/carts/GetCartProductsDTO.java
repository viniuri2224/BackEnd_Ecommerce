package com.vincenzoiurilli.Ecommerce.dto.carts;

import java.util.UUID;

public record GetCartProductsDTO(String productName, String productDescription, int quantity, float price) {
}
