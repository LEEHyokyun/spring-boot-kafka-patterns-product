package com.msa.product.infra.event;

import com.msa.product.infra.event.payload.EventPayload;
import com.msa.product.infra.event.payload.OrderCreatedEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Event Type에 대한 정의
 * - 주문
 * 각 Event Type Class를 type/topic에 대입 = why kafka, rather than rabbitmq
 * */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ORDER_CREATED(OrderCreatedEventPayload.class, Topic.CLOUD_NATIVE_MSA_ORDER)
    ;

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type){
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String CLOUD_NATIVE_MSA_ORDER = "CLOUD-NATIVE-MSA-ORDER";
    }
}
