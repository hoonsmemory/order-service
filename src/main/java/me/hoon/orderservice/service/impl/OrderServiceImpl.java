package me.hoon.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hoon.orderservice.domain.Order;
import me.hoon.orderservice.domain.dto.OrderRequestDto;
import me.hoon.orderservice.domain.dto.OrderResponseDto;
import me.hoon.orderservice.messagequeue.KafkaProducer;
import me.hoon.orderservice.messagequeue.OrderProducer;
import me.hoon.orderservice.repository.OrderRepository;
import me.hoon.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;

    private final OrderProducer orderProducer;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        orderRequestDto.createOrderId();
        orderRequestDto.setTotalPrice(orderRequestDto.getQuantity() * orderRequestDto.getUnitPrice());
        log.info("orderRequestDto : {}", orderRequestDto);

        /* JPA */
        //Order order = modelMapper.map(orderRequestDto, Order.class);
        //log.info("order : {}", order);

        //Order savedOrder = orderRepository.save(order);
        //OrderResponseDto responseDto = modelMapper.map(savedOrder, OrderResponseDto.class);
        //log.info("OrderResponseDto : {}", responseDto);


        //kafka 전달
        kafkaProducer.send("example-catalog-topic", orderRequestDto);
        orderProducer.send("orders", orderRequestDto);
        OrderResponseDto responseDto = modelMapper.map(orderRequestDto, OrderResponseDto.class);
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
