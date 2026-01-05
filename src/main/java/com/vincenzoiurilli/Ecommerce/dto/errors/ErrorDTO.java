package com.vincenzoiurilli.Ecommerce.dto.errors;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime timestamp) {
}
