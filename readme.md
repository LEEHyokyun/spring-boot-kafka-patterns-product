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

## 3. Additional KeyPoints

- JPA : Entity Graph

![img_8.png](img_8.png)