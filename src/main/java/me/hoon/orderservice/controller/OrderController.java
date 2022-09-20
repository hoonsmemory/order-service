package me.hoon.orderservice.controller;

import lombok.RequiredArgsConstructor;
import me.hoon.orderservice.domain.dto.OrderRequestDto;
import me.hoon.orderservice.domain.dto.OrderResponseDto;
import me.hoon.orderservice.service.OrderService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(("/order-service/orders"))
@RestController
public class OrderController {

    private final Environment env;
    private final OrderService orderService;


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}")
    public ResponseEntity createOrder(@PathVariable String userId,
                                      @RequestBody @Validated OrderRequestDto orderRequestDto,
                                      BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult);
        }

        orderRequestDto.setUserId(userId);
        OrderResponseDto responseDto = orderService.createOrder(orderRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getOrderByUserId(@PathVariable String userId) {

        List<OrderResponseDto> orders = orderService.getOrderByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
