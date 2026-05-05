package com.msa.product.service.order.eventhandler;

import com.msa.product.exception.ProductNotFoundException;
import com.msa.product.infra.kafka.event.Event;
import com.msa.product.infra.kafka.event.EventType;
import com.msa.product.infra.kafka.event.eventhandler.EventHandler;
import com.msa.product.infra.kafka.event.payload.OrderCreatedEventPayload;
import com.msa.product.model.entity.Product;
import com.msa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//EventHandler를 통한 최종 도메인 서비스 처리
@Component
@RequiredArgsConstructor
public class OrderCreatedEventHandler implements EventHandler<OrderCreatedEventPayload> {

    private final ProductRepository productRepository;

    @Override
    public void handle(Event<OrderCreatedEventPayload> event) {
        OrderCreatedEventPayload payload = event.getPayload();

        //entity 추출
        Product product = productRepository.findById(payload.getProductId()).orElseThrow(
                () -> new ProductNotFoundException(payload.getProductId())
        );

        //messaging
        product.updateQty(payload.getOrderedQty());

    }

    @Override
    public boolean supports(Event<OrderCreatedEventPayload> event) {
        return EventType.ORDER_CREATED == event.getEventType();
    }

    @Override
    public Long findProductId(Event<OrderCreatedEventPayload> event) {
        return event.getPayload().getProductId();
    }
}
