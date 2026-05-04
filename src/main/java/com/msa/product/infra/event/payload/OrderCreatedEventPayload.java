package com.msa.product.infra.event.payload;

import com.msa.product.infra.event.EventType;
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
    private Long productId;
    private Long orderedQty;
}
