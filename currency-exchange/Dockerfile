FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp
COPY /target/currency-exchange-*.jar currencyexchange.jar
COPY src src
COPY Dockerfile Dockerfile
COPY pom.xml pom.xml
ENTRYPOINT ["java", "-jar", "currencyexchange.jar"]
