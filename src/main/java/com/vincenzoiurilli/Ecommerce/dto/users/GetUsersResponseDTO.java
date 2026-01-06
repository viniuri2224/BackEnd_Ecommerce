package com.vincenzoiurilli.Ecommerce.dto.users;

import java.time.LocalDateTime;

public record GetUsersResponseDTO(String name, String surname, String email, LocalDateTime registrationDate, String role ,String status) {
}
