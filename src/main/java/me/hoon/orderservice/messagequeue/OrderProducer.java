package me.hoon.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hoon.orderservice.domain.dto.OrderRequestDto;
import me.hoon.orderservice.domain.dto.OrderResponseDto;
import me.hoon.orderservice.domain.dto.kafka.Field;
import me.hoon.orderservice.domain.dto.kafka.KafkaOrderDto;
import me.hoon.orderservice.domain.dto.kafka.Payload;
import me.hoon.orderservice.domain.dto.kafka.Schema;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    //DB에 반영될 필드명
    List<Field> fields = Arrays.asList(
        new Field("string", true, "order_id"),
        new Field("string", true, "user_id"),
        new Field("string", true, "product_id"),
        new Field("int32", true, "quantity"),
        new Field("int32", true, "unit_price"),
        new Field("int32", true, "total_price")
    );

    //DB schema 정보
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();


    public void send(String topic, OrderRequestDto orderRequestDto) {
        //DB에 반영될 값
        Payload payload = Payload.builder()
                .order_id(orderRequestDto.getOrderId())
                .user_id(orderRequestDto.getUserId())
                .product_id(orderRequestDto.getProductId())
                .quantity(orderRequestDto.getQuantity())
                .unit_price(orderRequestDto.getUnitPrice())
                .total_price(orderRequestDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        String jsonToString = "";

        try {
            jsonToString = objectMapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            log.error("error {}", e);
        }

        kafkaTemplate.send(topic, jsonToString);
        log.info("Kafka Producer sent data from the Order microservice {}", kafkaOrderDto);

    }

}
