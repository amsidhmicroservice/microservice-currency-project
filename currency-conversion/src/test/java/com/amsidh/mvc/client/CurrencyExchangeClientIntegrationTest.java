package com.amsidh.mvc.client;

import com.amsidh.mvc.exception.BadRequestException;
import com.amsidh.mvc.exception.NotFoundException;
import com.amsidh.mvc.model.CurrencyExchange;
import com.epages.wiremock.starter.WireMockTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@WireMockTest
public class CurrencyExchangeClientIntegrationTest {
    @Autowired
    public WireMockServer wireMockServer;
    public ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CurrencyExchangeClient currencyExchangeClient;

    @Test
    public void getCurrencyExchangeTest() throws JsonProcessingException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/currency-exchange/USD/to/INR"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(CurrencyExchange
                                .builder()
                                .currencyFrom("USD")
                                .currencyTo("INR")
                                .conversionMultiple(BigDecimal.valueOf(60l))
                                .build()))));
        CurrencyExchange currencyExchange = currencyExchangeClient.getCurrencyExchange("USD", "INR");
        assertTrue(currencyExchange.getConversionMultiple() != null);
        assertTrue(currencyExchange.getCurrencyFrom().equalsIgnoreCase("USD"));
        assertTrue(currencyExchange.getCurrencyTo().equalsIgnoreCase("INR"));
    }


    @Test
    public void getCurrencyExchangeWithBadRequestTest() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/currency-exchange/USD/to/INR"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        Exception exception = assertThrows(Exception.class, () -> currencyExchangeClient.getCurrencyExchange("USD", "INR"));
        assertTrue(exception instanceof BadRequestException);
        assertTrue(exception.getMessage().equalsIgnoreCase("Bad Request"));
    }

    @Test
    public void getCurrencyExchangeWithNotFoundRequestTest() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/currency-exchange/USD/to/INR"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        Exception exception = assertThrows(Exception.class, () -> currencyExchangeClient.getCurrencyExchange("USD", "INR"));
        assertTrue(exception instanceof NotFoundException);
        assertTrue(exception.getMessage().equalsIgnoreCase("Not Found"));
    }
}
