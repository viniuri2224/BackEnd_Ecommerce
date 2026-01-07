package com.vincenzoiurilli.Ecommerce.dto.products;

import com.vincenzoiurilli.Ecommerce.dto.categories.GetCategoriesResponseDTO;

import java.util.List;
import java.util.UUID;

public record ProductsResponseDTO(
        UUID productId,
        String name,
        String description,
        String type,
        String sellerEmail,
        float price,
        int warehouse_qty,
        String fileName,
        String fileUrl,
        String fileSize,
        String format,
        float weight,
        String dimensions,
        boolean shippingRequires,
        List<GetCategoriesResponseDTO> categories) {
}
