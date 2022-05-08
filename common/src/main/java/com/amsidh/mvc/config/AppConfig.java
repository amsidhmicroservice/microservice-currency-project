package com.amsidh.mvc.config;

import com.amsidh.mvc.model.InstanceInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    private static final String HOST_NAME = "HOSTNAME";
    @Value("${" + HOST_NAME + ":local}")
    private String nodeName;
    @Value("${info.app.version:NoVersion}")
    private String projectVersion;
    @Value("${server.port:8080}")
    private Integer port;
    @Value("${spring.application.name:noname}")
    private String serviceName;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        return objectMapper;
    }

    @Bean
    public InstanceInfo getInstanceInfo() {
        return InstanceInfo.builder().serviceName(serviceName).nodeName(nodeName).projectVersion(projectVersion).port(port).build();
    }
}
