package com.vincenzoiurilli.Ecommerce.dto.categories;

import java.util.UUID;

public record GetCategoriesResponseDTO(UUID id, String name, String description, String type) {
}
