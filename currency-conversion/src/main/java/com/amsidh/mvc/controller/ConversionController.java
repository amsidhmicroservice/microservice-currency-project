package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.CurrencyConversion;
import com.amsidh.mvc.model.CurrencyExchange;
import com.amsidh.mvc.service.CurrencyConversionService;
import com.amsidh.mvc.service.InstanceInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static com.amsidh.mvc.common.Constant.CURRENCY_EXCHANGE_API_CALL_CIRCUIT_BREAKER;
import static com.amsidh.mvc.common.Constant.CURRENCY_EXCHANGE_API_CALL_RETRY;
import static io.github.resilience4j.decorators.Decorators.ofSupplier;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/currency-conversion")
public class ConversionController {

    private final CurrencyConversionService currencyConversionService;

    @GetMapping(value = "/health", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String healthCheck() {
        return "{\"status\":\"Currency-Conversion Service is up and running\"}";
    }

    //http://35.222.88.162:8282/currency-conversion/from/USD/to/INR/quantity/100
    @SneakyThrows
    @GetMapping(value = {"/from/{currencyFrom}/to/{currencyTo}/quantity/{quantity}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public CurrencyConversion convertCurrency(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo, @PathVariable("quantity") BigDecimal quantity) {
        log.info("=======================================Start Request================================================");
        CurrencyConversion currencyConversion = currencyConversionService.getCurrencyConversion(currencyFrom, currencyTo, quantity);
        log.info("=======================================End Request================================================");
        return currencyConversion;
    }
}
