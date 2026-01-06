package com.vincenzoiurilli.Ecommerce.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "La mail è obbligatoria!")
        String email,
        @NotBlank(message = "La password è obbligatoria!")
        String password) {
}
