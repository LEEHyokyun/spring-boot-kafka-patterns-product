package com.msa.product.infra.consumer;

import com.msa.product.infra.event.Event;
import com.msa.product.infra.event.payload.EventPayload;
import com.msa.product.infra.event.EventType;
import com.msa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final ProductService productService;

    @KafkaListener(
            topics = {
                EventType.Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT
            }
    )
    public void listen(String message, Acknowledgment ack) {
        log.info("[OrderEventConsumer.listen][INFO] received message={}", message);
        Event<EventPayload> event = Event.fromJsonStringData(message);

        if(event != null){
            productService.orderProducts(event);
        }

        /*
         * 메시지 처리 완료, Kafka 측에 성공 응답
         * 이 이후에 commit이 이루어져야 하므로 auto-commit = false로 설정하는 것이다.
         * offset commit
         * */
        ack.acknowledge();
    }
}
