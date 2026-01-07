package com.vincenzoiurilli.Ecommerce.dto.products;

public record ProductsResponseDTO(
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
        boolean shippingRequires) {
}
