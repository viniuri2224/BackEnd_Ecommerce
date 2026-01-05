package com.vincenzoiurilli.Ecommerce.dto.products;

import java.util.UUID;

public record UpdatedPhysicalProductResponseDTO(UUID productId, String name, String Description, String sellerEmail, float price, int qty, float weight, String dimensions, boolean shipping_required) {
}
