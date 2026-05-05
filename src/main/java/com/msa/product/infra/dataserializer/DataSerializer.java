package com.msa.product.infra.dataserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public final class DataSerializer {
    //직렬화, 역직렬화를 위한 util
    private static final ObjectMapper objectMapper = initialize();

    //시간 직렬화
    //역직렬화 시 지정되어있지 않은 속성/프로퍼티라도 예외가 발생하지 않도록 설정
    private static ObjectMapper initialize() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    //직렬화 : Object to String
    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object); //Object(EventPayLoad Class) > Json
        } catch (JsonProcessingException e) {
            /*
             * error 발생 시 null 반환
             * */
            log.error("[ERROR][DataSerializer.serialize] object={}", object, e);
            return null;
        }
    }

    //역직렬화 1차 (Json String > Object from Kafka) : JsonString to Object(EventRaw)
    public static <T> T deserialize(String jsonData, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonData, clazz);
        } catch (JsonProcessingException e) {
            /*
             * error 발생 시 null 반환
             * */
            log.error("[DataSerializer.deserialize] jsonData={}, clazz={}", jsonData, clazz, e);
            return null;
        }
    }

    //역직렬화 2차(Object Object) >  : Object to Object
    public static <T> T deserialize(Object data, Class<T> clazz) {
        return objectMapper.convertValue(data, clazz);
    }


}
