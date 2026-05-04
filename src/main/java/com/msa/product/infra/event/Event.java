package com.msa.product.infra.event;

import com.msa.product.infra.dataserializer.DataSerializer;
import com.msa.product.infra.event.payload.EventPayload;
import lombok.Getter;

/*
 * Event 통신을 위한 클래스이자 프로토콜
 * - 이벤트에 대해 eventId를 기반으로 구별한다.
 * - 이벤트 객체를 생성한다(*Kafka로 전달할때 Json 직렬화 및 데이터 받아올때 역직렬화 필요).
 * - 이벤트가 어떤 타입인지 나타낸다.
 * - 이벤트가 어떤 데이터를 가지고 있는지 나타낸다.
 * */
@Getter
public class Event<T extends EventPayload> {
    private EventType eventType;
    private T payload;

    //Kafka 통신을 위한 직렬화 : Event > Json
    public String toJson(){
        return DataSerializer.serialize(this);
    }

    //Kafka 통신을 위한 역직렬화 : Json > Event
    public static Event<EventPayload> fromJson(String json){
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);

        if(eventRaw == null){
            return null;
        }

        Event<EventPayload> event = new Event<>();
        event.eventType = EventType.from(eventRaw.getEventType());
        event.payload = DataSerializer.deserialize(eventRaw.getPayload(), event.eventType.getPayloadClass());

        return event;
    }

    //=Kafka로 부터 직접적으로 전달 받는 역직렬화된 메시지
    @Getter
    private static class EventRaw {
        //역직렬화 = Producer에서 보내는 payload 혹은 직렬화된 객체 구조와 일치해야 함
        private String eventType;
        private Object payload;
    }
}
