package com.amsidh.mvc.currencyconversion;

import com.amsidh.mvc.model.CurrencyExchange;
import com.epages.wiremock.starter.WireMockTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
@WireMockTest
class CurrencyConversionApplicationTest {

    @Autowired
    public WireMockServer wireMockServer;

    @Autowired
    public ObjectMapper objectMapper;


    @Test
    void contextLoads() throws JsonProcessingException {
        log.info("Running contextLoads test method from CurrencyConversionApplicationTests class");
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/currency-exchange/USD/to/INR"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(CurrencyExchange
                                .builder()
                                .currencyFrom("USD")
                                .currencyTo("INR")
                                .conversionMultiple(BigDecimal.valueOf(60L))
                                .build()))));
    }

}


