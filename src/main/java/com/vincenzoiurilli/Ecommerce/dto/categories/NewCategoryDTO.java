package com.vincenzoiurilli.Ecommerce.dto.categories;

import jakarta.validation.constraints.NotBlank;

public record NewCategoryDTO(
        @NotBlank(message = "Il nome della cetegoria è obbligatorio")
        String name,
        @NotBlank(message = "La descrizione è obbligatoria")
        String description,
        @NotBlank(message = "Il tipo della cetegoria è obbligatorio")
        String type) {
}
