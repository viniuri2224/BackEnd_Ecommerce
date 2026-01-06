package com.vincenzoiurilli.Ecommerce.dto.carts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record NewCartProductQtyDTO(
        @NotNull(message = "Inserire una quantità")
        @PositiveOrZero(message = "La quantità non può essere minore di zero")
        int quantity) {
}
