package com.vincenzoiurilli.Ecommerce.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewUserDTO(

        @NotBlank(message = "Il nome è un campo obbligatorio")
        String name,
        @NotBlank(message = "Il cognome è un campo obbligatorio")
        String surname,
        @NotBlank(message = "La mail è un campo obbligatorio")
        @Email(message = "L'email non è nel formato corretto")
        String email,
        @NotBlank(message = "Il password è un campo obbligatorio")
        //La stringa deve essere lunga almeno 8 caratteri e contenere almeno una lettera maiuscola, una minuscola e un numero.
        //@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
        String password,
        @NotBlank(message = "Il ruolo è un campo obbligatorio")
        String role) {
}
