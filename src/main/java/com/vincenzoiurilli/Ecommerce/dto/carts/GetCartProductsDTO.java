package com.vincenzoiurilli.Ecommerce.dto.carts;

import java.util.UUID;

public record GetCartProductsDTO(UUID productId, UUID cartId, int quantity, float price) {
}
