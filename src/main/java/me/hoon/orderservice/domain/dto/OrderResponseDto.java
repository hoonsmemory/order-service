package me.hoon.orderservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDto {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;
    private String orderId;

    public OrderResponseDto() {
    }

    @Builder
    public OrderResponseDto(String productId, Integer quantity, Integer unitPrice, Integer totalPrice, Date createdAt, String orderId, boolean result, String errorCode, String errorMessage) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderId = orderId;
    }
}
