FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp
COPY /target/currency-conversion-*.jar currencyconversion.jar
COPY src src
COPY Dockerfile Dockerfile
COPY pom.xml pom.xml
ENTRYPOINT ["java", "-jar", "currencyconversion.jar"]