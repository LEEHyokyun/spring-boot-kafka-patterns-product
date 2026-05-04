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
            return objectMapper.writeValueAsString(object); //Object(EventPayLoad Class) > String
        } catch (JsonProcessingException e) {
            /*
             * error 발생 시 null 반환
             * */
            log.error("[ERROR][DataSerializer.serialize] object={}", object, e);
            return null;
        }
    }

    //역직렬화 : Object to Type
    public static <T> T deserialize(Object data, Class<T> clazz) {
        return objectMapper.convertValue(data, clazz);
    }

    //역직렬화 : String to Type
    public static <T> T deserialize(String data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            /*
             * error 발생 시 null 반환
             * */
            log.error("[DataSerializer.deserialize] data={}, clazz={}", data, clazz, e);
            return null;
        }
    }
}
