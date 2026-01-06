package com.vincenzoiurilli.Ecommerce.dto.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NewDigitalProductDTO(
        @NotBlank(message = "Il nome è obbligatorio")
        String name,
        @NotBlank(message = "La descrizione è obbligatoria")
        String description,
        @NotNull(message = "Inserire un prezzo")
        @Positive(message = "Il prezzo deve essere maggiore di zero")
        float price,
        @NotNull(message = "Inserire la quantità a magazzino")
        @PositiveOrZero(message = "La quantità deve essere maggiore o uguale a zero") //In caso volessi creare il prodotto e poi gestirne le quantità
        int quantity,
        @NotBlank(message = "Il nome del file è obbligatorio")
        String file_name,
        @NotBlank(message = "Il percorso del file è obbligatorio")
        String file_url,
        @NotBlank(message = "La dimensione del file è obbligatoria")
        String file_size,
        @NotBlank(message = "Il formato è obbligatorio")
        String format) {
}
