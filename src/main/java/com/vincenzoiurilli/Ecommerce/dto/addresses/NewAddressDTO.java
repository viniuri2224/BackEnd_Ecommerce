package com.vincenzoiurilli.Ecommerce.dto.addresses;

import jakarta.validation.constraints.NotBlank;

public record NewAddressDTO(
        @NotBlank(message = "L'indirizzo è obbligatorio ")
        String address,
        @NotBlank(message = "La città è obbligatoria ")
        String city,
        @NotBlank(message = "La regione è obbligatoria ")
        String region,
        @NotBlank(message = "Lo stato è obbligatorio")
        String state) {
}
