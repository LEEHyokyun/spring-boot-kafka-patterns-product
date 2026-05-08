package com.msa.product.infra.kafka.event;

import com.msa.product.infra.kafka.dataserializer.DataSerializer;
import com.msa.product.util.event.payload.EventPayload;
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
    public static Event<EventPayload> fromJsonStringData(String jsonStringData){
        /*
        * 애초에 json 형태로 변경하여 보냈으므로,
        * Producer 측에서 포맷을 잘 맞춰서 보내었다면 오류 확률은 0.
        * */
        EventRaw eventRaw = DataSerializer.deserialize(jsonStringData, EventRaw.class);

        if(eventRaw == null){
            return null;
        }

        Event<EventPayload> event = new Event<>();
        event.eventType = EventType.from(eventRaw.getEventType());
        /*
        * key point : eventRaw payload(Kafka로부터 1차 역직렬화한 payload)를
        * eventType의 payloadClass 로 정의된 Object 형태로 최종 역직렬화한다.
        * */
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
