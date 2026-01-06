package com.vincenzoiurilli.Ecommerce.dto.products;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NewPhysicalProductDTO(
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
        @NotNull(message = "Inserire un peso")
        @Positive(message = "Il peso deve essere maggiore di zero")
        float weight,
        @NotBlank(message = "Inserire delle dimensioni")
        String dimensions,
        @NotBlank(message = "Specificare se è da spedire")
        boolean shipping_required) {
}
