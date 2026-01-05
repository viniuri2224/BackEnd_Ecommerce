package com.vincenzoiurilli.Ecommerce.dto.products;

import java.util.UUID;

public record UpdatedDigitalProductResponseDTO(UUID productId, String name, String Description, String sellerEmail, float price, int qty, String file_name, String file_url, String file_size, String format) {
}
