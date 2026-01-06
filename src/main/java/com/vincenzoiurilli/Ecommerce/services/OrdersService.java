package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.orders.GetOrderItemsResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.orders.GetOrdersResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.orders.NewOrderResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.*;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.exceptions.UnauthorizedException;
import com.vincenzoiurilli.Ecommerce.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CartProductsService cartProductsService;

    @Autowired
    private OrderProductsService orderProductsService;

    public NewOrderResponseDTO createOrder(Users currentUser) {
        Carts cart =  currentUser.getCart();

        List<CartProducts> cartItem = this.cartProductsService.getCartItems(cart.getId());

        Orders order = new Orders(currentUser, LocalDateTime.now());

        for (CartProducts item : cartItem) {
            OrderProducts orderItems = new OrderProducts(order, item.getProduct(), item.getProductQuantity(), item.getProductPrice());
            this.orderProductsService.createOrderItems(orderItems);
            this.cartProductsService.deleteCartItemAfterOrder(item);
        }
        Orders savedOrder = this.ordersRepository.save(order);
        return new NewOrderResponseDTO(savedOrder.getId());
    }

    public List<GetOrdersResponseDTO> getOrders(Users currentUser) {
        List<Orders> userOrders = this.ordersRepository.getOrders(currentUser.getId());

        if (userOrders.isEmpty()) {
            throw new NotFoundException("Orders not found");
        }

        List<GetOrdersResponseDTO> dtos = new ArrayList<>();
        for (Orders order : userOrders) {
            List<GetOrderItemsResponseDTO> itemsDtos = new ArrayList<>();
            for(OrderProducts orderItem : order.getOrderProducts()) {
                GetOrderItemsResponseDTO itemsDto = new GetOrderItemsResponseDTO(orderItem.getProduct().getName(), orderItem.getProduct().getDescription(), orderItem.getQuantity(), orderItem.getPrice_at_purchase());
                itemsDtos.add(itemsDto);
            }
            GetOrdersResponseDTO dto = new GetOrdersResponseDTO(order.getId(), order.getOrderDate(), itemsDtos);
            dtos.add(dto);
        }

        return dtos;
    }

    public GetOrdersResponseDTO getOrderDetails(UUID orderId, Users currentUser) {

        Orders userOrder = this.ordersRepository.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));
        if(!(userOrder.getUser().getId().equals(currentUser.getId()))){
            throw new UnauthorizedException("You are not allowed to see this order!");
        }
        List<GetOrderItemsResponseDTO> itemsDtos = new ArrayList<>();

        for(OrderProducts orderItem : userOrder.getOrderProducts()) {
            GetOrderItemsResponseDTO itemsDto = new GetOrderItemsResponseDTO(orderItem.getProduct().getName(), orderItem.getProduct().getDescription(), orderItem.getQuantity(), orderItem.getPrice_at_purchase());
            itemsDtos.add(itemsDto);
        }

        return new GetOrdersResponseDTO(userOrder.getId(), userOrder.getOrderDate(), itemsDtos);
    }

}
