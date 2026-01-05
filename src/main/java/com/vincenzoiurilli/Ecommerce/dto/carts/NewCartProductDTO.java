package com.vincenzoiurilli.Ecommerce.dto.carts;

import java.util.UUID;

public record NewCartProductDTO(UUID productId, UUID cartId, Integer quantity) {
}
