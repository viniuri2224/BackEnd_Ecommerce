package com.vincenzoiurilli.Ecommerce.dto.carts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record NewCartProductDTO(
        @NotNull(message = "Il prodotto è obbligatorio")
        UUID productId,
        @NotNull(message = "Il carrello è obbligatorio")
        UUID cartId,
        @NotNull(message = "Inserire una quantità")
        @PositiveOrZero(message = "La quantità non può essere minore di zero")
        Integer quantity) {
}
