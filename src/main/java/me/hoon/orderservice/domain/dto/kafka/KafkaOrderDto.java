package me.hoon.orderservice.domain.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class KafkaOrderDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
