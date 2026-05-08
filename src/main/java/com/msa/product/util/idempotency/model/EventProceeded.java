package com.msa.product.util.idempotency.model;

import com.msa.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "event_proceeded")
@NoArgsConstructor
@AllArgsConstructor
public class EventProceeded {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventProceededId;

    private String idempotencyId;

    private String eventId;

    private String eventPayload;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EventProceeded from(String idempotencyId, String eventId, String eventPayload){

        EventProceeded eventProceeded = new EventProceeded();

        eventProceeded.idempotencyId = idempotencyId;
        eventProceeded.eventId = eventId;
        eventProceeded.eventPayload = eventPayload;
        eventProceeded.createdAt = LocalDateTime.now();
        eventProceeded.updatedAt = eventProceeded.createdAt;

        return eventProceeded;
    }
}
