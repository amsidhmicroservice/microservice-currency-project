package com.amsidh.mvc.currencyconversion.feign;

import com.amsidh.mvc.model.CurrencyExchange;
import com.epages.wiremock.starter.WireMockTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@WireMockTest
public class CurrencyExchangeClientIntegrationTest {
    @Autowired
    public WireMockServer wireMockServer;

    @Autowired
    public ObjectMapper objectMapper;

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
                                .conversionMultiple(BigDecimal.valueOf(60L))
                                .build()))));
        CurrencyExchange currencyExchange = currencyExchangeClient.getCurrencyExchange("USD", "INR");
        assertNotNull(currencyExchange.getConversionMultiple());
        assertTrue(currencyExchange.getCurrencyFrom().equalsIgnoreCase("USD"));
        assertTrue(currencyExchange.getCurrencyTo().equalsIgnoreCase("INR"));
    }


    @Test
    public void getCurrencyExchangeWithBadRequestTest() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/currency-exchange/USD/to/INR"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())));
        Exception exception = assertThrows(Exception.class, () -> currencyExchangeClient.getCurrencyExchange("USD", "INR"));
        assertTrue(exception instanceof FeignException.FeignClientException.BadRequest);
        assertTrue(exception.getMessage().contains("Bad Request"));
    }

    @Test
    public void getCurrencyExchangeWithNotFoundRequestTest() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/currency-exchange/USD/to/INR"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));
        Exception exception = assertThrows(Exception.class, () -> currencyExchangeClient.getCurrencyExchange("USD", "INR"));
        assertTrue(exception instanceof FeignException.FeignClientException.NotFound);
        assertTrue(exception.getMessage().contains("Not Found"));
    }
}
