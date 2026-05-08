package com.msa.product.util.event.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//for order created event
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEventPayload implements EventPayload {
    private String idempotencyId;
    private String eventId;
    private Long productId;
    private Long orderedQty;

    public String toJson() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Event toJson 실패", e);
        }
    }
}
