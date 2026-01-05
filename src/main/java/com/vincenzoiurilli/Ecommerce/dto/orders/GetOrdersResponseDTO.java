package com.vincenzoiurilli.Ecommerce.dto.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetOrdersResponseDTO(UUID orderId, LocalDateTime createdAt, List<GetOrderItemsResponseDTO> orderItems) {
}
