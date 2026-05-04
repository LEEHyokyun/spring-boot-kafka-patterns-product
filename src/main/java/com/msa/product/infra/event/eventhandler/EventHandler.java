package com.msa.product.infra.event.eventhandler;

import com.msa.product.infra.event.Event;
import com.msa.product.infra.event.payload.EventPayload;

/*
 * Event가 발생하여 Listener에서 이를 읽고 처리하기 위한 핸들러
 * 각 클래스의 행동을 정의해주는 Strategy pattern(전략패턴) 사용.
 * */
/*
 * 인터페이스 구현 시점에서 본인이 다룰 이벤트 객체 형태를 강제한다.
 * 구현체가 반드시 "자기가 다룰 이벤트 타입"을 명시하도록 강제할 수 있기 때문.
 * -> T : OK, ? : NOT OK.
 * */
public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);

    boolean supports(Event<T> event);

    Long findProductId(Event<T> event);

}
