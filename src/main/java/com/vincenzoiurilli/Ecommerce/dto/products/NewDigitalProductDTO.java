package com.vincenzoiurilli.Ecommerce.dto.products;

public record NewDigitalProductDTO(String name, String description, float price, int quantity, String file_name, String file_url, String file_size, String format) {
}
