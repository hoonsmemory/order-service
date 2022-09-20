package me.hoon.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.hoon.orderservice.domain.Order;
import me.hoon.orderservice.domain.dto.OrderRequestDto;
import me.hoon.orderservice.domain.dto.OrderResponseDto;
import me.hoon.orderservice.repository.OrderRepository;
import me.hoon.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        orderRequestDto.createOrderId();
        orderRequestDto.setTotalPrice(orderRequestDto.getQuantity() * orderRequestDto.getUnitPrice());

        log.info("orderRequestDto : {}", orderRequestDto);

        Order order = modelMapper.map(orderRequestDto, Order.class);

        log.info("order : {}", order);

        Order savedOrder = orderRepository.save(order);
        OrderResponseDto responseDto = modelMapper.map(savedOrder, OrderResponseDto.class);

        log.info("OrderResponseDto : {}", responseDto);

        return responseDto;
    }

    @Override
    public OrderResponseDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        OrderResponseDto responseDto = modelMapper.map(order, OrderResponseDto.class);
        return responseDto;
    }

    @Override
    public List<OrderResponseDto> getOrderByUserId(String userId) {

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        Iterable<Order> orders = orderRepository.findByUserId(userId);

        //변환 작업
        orders.forEach(x -> orderResponseDtoList.add(modelMapper.map(x, OrderResponseDto.class)));

        return orderResponseDtoList;
    }
}
