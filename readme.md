## 1.  개요

> 분산환경에서 Kafka 활용 시 발생가능한 다양한 문제상황을 가정하고 이를 Trouble Shooting.

- Java ver 21.
- Spring Boot ver 3.5.0 / Spring Cloud 2025.0.x Northfields
- apache/Kafka 3.8.0
- MySQL 8.0.42

> System
![img_7.png](img_7.png)


> SAGA
![img_5.png](img_5.png)


> Consumer 
![img_6.png](img_6.png)

## 2. WAS Configs

| Spring Application                       | Host Port | 비고    |
|------------------------------------------|-----------|-------|
| spring-boot-kafka-patterns-order         | 60010     | Producer |
| spring-boot-kafka-patterns-product       | 60011      | Consumer |
| spring-boot-kakfa-patterns-kafka         | 9092      | Kafka |
| spring-boot-kafka-patterns-mysql-order   | 3307      | Order DB |
| spring-boot-kafka-patterns-mysql-product | 3308      | Product DB |

## 3. Trouble Shootings

> Kafka의 메시지 유실 상황에 대한 대응 방안

## 3-1. Consumer의 메시지 처리 실패

``
2026-05-05T19:32:49.713+09:00 ERROR 9584 --- [spring-boot-kafka-patterns-product] [ntainer#0-0-C-1] o.s.kafka.listener.DefaultErrorHandler   : Backoff FixedBackOff{interval=0, currentAttempts=10, maxAttempts=9} exhausted for ORDER.CREATED-0@4
``

기본설정은 무한 재시도(interval = 0, 즉시 재시도, 최대 재시도 횟수는 최초 시도 포함 10번)

``
2026-05-05T20:00:52.797+09:00 ERROR 19612 --- [spring-boot-kafka-patterns-product] [etry-4000-0-C-1] k.r.DeadLetterPublishingRecovererFactory : Record: topic = ORDER.CREATED-retry-4000, partition = 0, offset = 0, main topic = ORDER.CREATED threw an error at topic ORDER.CREATED-retry-4000 and won't be retried. Sending to DLT with name ORDER.CREATED-dlt.
``

구체적인 재시도 정책이 정립될 경우, 더이상 무의미한 재시도를 시도하지 않고 DLT로 전송(send to DLT),

## 4. Additional KeyPoints

- JPA : Entity Graph

![img_8.png](img_8.png)