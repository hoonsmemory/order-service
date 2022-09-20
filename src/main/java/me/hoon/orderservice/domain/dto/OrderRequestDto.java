package me.hoon.orderservice.domain.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
public class OrderRequestDto implements Serializable {

    @NotBlank
    private String productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer unitPrice;

    private Integer totalPrice;

    private String orderId;
    private String userId;

    public OrderRequestDto() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void createOrderId() {
        this.orderId = UUID.randomUUID().toString();
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
