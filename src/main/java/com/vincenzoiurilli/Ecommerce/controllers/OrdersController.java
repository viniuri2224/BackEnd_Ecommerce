package com.vincenzoiurilli.Ecommerce.controllers;

import com.vincenzoiurilli.Ecommerce.dto.orders.GetOrdersResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.orders.NewOrderResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public NewOrderResponseDTO createOrder(@AuthenticationPrincipal Users currentUser){
        return this.ordersService.createOrder(currentUser);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping
    public List<GetOrdersResponseDTO> getOrders(@AuthenticationPrincipal Users currentUser){
        return this.ordersService.getOrders(currentUser);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/{orderId}")
    public GetOrdersResponseDTO getOrderDetails(@PathVariable UUID orderId, @AuthenticationPrincipal Users currentUser){
        return this.ordersService.getOrderDetails(orderId, currentUser);
    }


}
