package com.msa.product.util.idempotency.handler;

import com.msa.product.exception.ProductNotFoundException;
import com.msa.product.infra.kafka.event.Event;
import com.msa.product.infra.kafka.event.EventType;
import com.msa.product.util.event.payload.OrderCreatedEventPayload;
import com.msa.product.model.entity.Product;
import com.msa.product.repository.ProductRepository;
import com.msa.product.util.event.handler.EventHandler;
import com.msa.product.util.idempotency.model.EventProceeded;
import com.msa.product.util.idempotency.repository.EventProceededRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//EventHandler를 통한 최종 도메인 서비스 처리
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedIdempotencyHandler implements IdempotencyHandler<OrderCreatedEventPayload> {

    private final EventProceededRepository eventProceededRepository;

    @Override
    public void handle(Event<OrderCreatedEventPayload> event) {
        OrderCreatedEventPayload payload = event.getPayload();

        //entity 추출
        try {
            eventProceededRepository.save(
                    EventProceeded.from(
                            payload.getIdempotencyId(),
                            payload.getEventId(),
                            payload.toJson()
                    )
            );
        } catch (RuntimeException e) {
            log.info("[OrderCreatedIdempotnecyHandler.handle][INFO] 이미 처리된 페이로드입니다.");
            return;
        }

    }

    @Override
    public boolean supports(Event<OrderCreatedEventPayload> event) {
        return EventType.ORDER_CREATED == event.getEventType();
    }

    @Override
    public String findIdempotencyId(Event<OrderCreatedEventPayload> event) {
        return event.getPayload().getIdempotencyId();
    }

    @Override
    public String findEventId(Event<OrderCreatedEventPayload> event) {
        return event.getPayload().getEventId();
    }


}
