package com.vincenzoiurilli.Ecommerce.dto.products;

import java.util.UUID;

public record ProductsStatisticDTO(UUID productId, String Name, int quantity, float earnings) {
}
