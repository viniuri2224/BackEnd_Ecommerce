package com.vincenzoiurilli.Ecommerce.dto.users;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetUsersResponseDTO(UUID id, String name, String surname, String email, LocalDateTime registrationDate, String role , String status) {
}
