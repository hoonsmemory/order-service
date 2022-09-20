package me.hoon.orderservice.service;

import me.hoon.orderservice.domain.dto.OrderRequestDto;
import me.hoon.orderservice.domain.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrderByOrderId(String orderId);
    List<OrderResponseDto> getOrderByUserId(String userId);
}
