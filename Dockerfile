FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/cloud-native-msa-product-1.jar cloud-native-msa-product.jar

VOLUME /tmp

ENTRYPOINT ["java","-jar","cloud-native-msa-product.jar"]