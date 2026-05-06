package com.msa.product.service.order.consumer;

import com.msa.product.infra.kafka.event.Event;
import com.msa.product.infra.kafka.event.payload.EventPayload;
import com.msa.product.infra.kafka.event.EventType;
import com.msa.product.service.ProductService;
import com.msa.product.service.order.service.ProductOrderDLTService;
import com.msa.product.service.order.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final ProductService productService;
    private final ProductOrderService productOrderService;
    private final ProductOrderDLTService productOrderDLTService;

    private final String DLT_GROUP_ID = "spring-boot-kafka-patterns-product-dlt";

    @KafkaListener(
            topics = {
                EventType.Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_PRODUCT
            }
    )
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 500, multiplier = 2),
            /*
            * ORDER.CREATED.DLT - DLT
            * */
            dltTopicSuffix = ".DLT"
    )
    public void listen1(String message, Acknowledgment ack) {
        log.info("[OrderEventConsumer.listen][INFO] received message={}", message);
        Event<EventPayload> event = Event.fromJsonStringData(message);

        if(event != null){
            productOrderService.orderProducts(event);
        }

        /*
         * 메시지 처리 완료, Kafka 측에 성공 응답
         * 이 이후에 commit이 이루어져야 하므로 auto-commit = false로 설정하는 것이다.
         * offset commit
         * */
        ack.acknowledge();
    }

    @KafkaListener(
            topics = {
                    EventType.Topic.SPRING_BOOT_KAFKA_PATTERNS_ORDER_CREATED_DLT
            },
            groupId = DLT_GROUP_ID
    )
    public void listen2(String message, Acknowledgment ack){
        log.info("[OrderEventConsumer.listen][INFO] received dlt message={}", message);
        Event<EventPayload> event = Event.fromJsonStringData(message);

        if(event != null){
            productOrderDLTService.orderProducts(event);
        }

        /*
         * 메시지 처리 완료, Kafka 측에 성공 응답
         * 이 이후에 commit이 이루어져야 하므로 auto-commit = false로 설정하는 것이다.
         * offset commit
         * */
        ack.acknowledge();
    }
}
