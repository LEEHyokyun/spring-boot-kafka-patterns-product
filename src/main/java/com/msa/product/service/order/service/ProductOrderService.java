package com.msa.product.service.order.service;

import com.msa.product.infra.kafka.event.Event;
import com.msa.product.infra.kafka.event.eventhandler.EventHandler;
import com.msa.product.infra.kafka.event.payload.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOrderService {

    /*
     * 이벤트핸들러(List)
     * Event 객체를 매개변수로 전달받아 해당하는 이벤트핸들러를 매핑해주기 위함
     * 참고로 Spring Framework에 의해 해당 리스트의 생성자 주입 시점에 해당 구현체들이 주입되어 할당됨
     * */
    private final List<EventHandler> eventHandlers;

    @Transactional
    public void orderProducts(Event<EventPayload> event){
        EventHandler<EventPayload> eventHandler = findEventHandler(event);

        if(eventHandler == null){
            log.error("[ERROR][ProductService] No Such EventHandler found");
            return;
        }

        eventHandler.handle(event);
    }

    private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                /*
                 * Wrapper -> Object
                 * */
                .orElse(null);
    }

}
