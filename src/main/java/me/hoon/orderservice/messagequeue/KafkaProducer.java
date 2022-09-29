package me.hoon.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hoon.orderservice.domain.dto.OrderRequestDto;
import me.hoon.orderservice.domain.dto.OrderResponseDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderResponseDto send(String topic, OrderRequestDto orderRequestDto) {
        String jsonToString = "";

        try {
            jsonToString = objectMapper.writeValueAsString(orderRequestDto);
        } catch(JsonProcessingException e) {
            log.error("error = {}", e);
        }

        kafkaTemplate.send(topic, jsonToString);
        log.info("Kafka Producer sent data from the Order microservice : {}", jsonToString);

        return null;
    }
}
