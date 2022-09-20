package me.hoon.orderservice.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.BindingResult;

import java.io.IOException;

@Slf4j
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<BindingResult> {
    @Override
    public void serialize(BindingResult bindingResult, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        bindingResult.getFieldErrors().forEach(e -> {
            log.error(e.getField());
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("field", e.getField());
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("getDefaultMessage", e.getDefaultMessage());
                Object rejectedValue = e.getRejectedValue();
                if(rejectedValue != null) {
                    jsonGenerator.writeStringField("rejectedValue", rejectedValue.toString());
                }
                jsonGenerator.writeEndObject();;
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        });

        bindingResult.getGlobalErrors().forEach(e -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("getDefaultMessage", e.getDefaultMessage());
                jsonGenerator.writeEndObject();;
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        });

        jsonGenerator.writeEndArray();
    }
}