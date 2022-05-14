package com.amsidh.mvc.currencyconversion.service.impl;

import com.amsidh.mvc.currencyconversion.service.CurrencyConversionService;
import com.amsidh.mvc.feign.CurrencyExchangeClient;
import com.amsidh.mvc.model.CurrencyConversion;
import com.amsidh.mvc.model.CurrencyExchange;
import com.amsidh.mvc.model.InstanceInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final CurrencyExchangeClient currencyExchangeClient;
    private final InstanceInfo instanceInfo;

    private final ObjectMapper objectMapper;

    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final BulkheadRegistry bulkheadRegistry;

    @SneakyThrows
    @Override
    public CurrencyConversion getCurrencyConversion(String currencyFrom, String currencyTo, BigDecimal quantity) {
        CurrencyConversion currencyConversion = CurrencyConversion.builder().from(currencyFrom).to(currencyTo).quantity(quantity).instanceInfo(instanceInfo).build();
        log.info("Calling currency-exchange service with currencyFrom {}  and currencyTo {}", currencyFrom, currencyTo);

        CurrencyExchange exchange = Decorators.ofSupplier(() -> currencyExchangeClient.getCurrencyExchange(currencyFrom, currencyTo)).withRetry(this.retryRegistry.retry("currency-exchange-api-call-circuit-breaker")).withCircuitBreaker(this.circuitBreakerRegistry.circuitBreaker("currency-exchange-api-call-circuit-breaker")).withBulkhead(bulkheadRegistry.bulkhead("currency-exchange-api-call-bulkhead")).withFallback((currencyExchange, throwable) -> {
            log.error("Returning Fallback default");
            return throwable != null ? CurrencyExchange.builder().build() : currencyExchange;
        }).decorate().get();

        log.info("Response received from currency-exchange service {}", exchange != null ? objectMapper.writeValueAsString(exchange) : null);
        Optional.ofNullable(exchange).ifPresent(currencyExchange -> {
            currencyConversion.setId(exchange.getId());
            currencyConversion.setConversionMultiple(exchange.getConversionMultiple());
            currencyConversion.setTotalCalculatedAmount(quantity.multiply(exchange.getConversionMultiple() != null ? exchange.getConversionMultiple() : new BigDecimal(0)));
            currencyConversion.getInstanceInfo().setInstanceInfo(exchange.getInstanceInfo());
        });
        return currencyConversion;
    }


}
