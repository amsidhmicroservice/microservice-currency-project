package com.amsidh.mvc.feign;

import com.amsidh.mvc.model.CurrencyExchange;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeClient {
    Logger log = LoggerFactory.getLogger(CurrencyExchangeClient.class);

    @RequestMapping(method = RequestMethod.GET, value = "/currency-exchange/{currencyFrom}/to/{currencyTo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Retry(name = "currency-exchange-api-call-retry", fallbackMethod = "getCurrencyExchangeFallBack")
    @CircuitBreaker(name = "currency-exchange-api-call-circuit-breaker")
    @Bulkhead(name = "currency-exchange-api-call-bulkhead")
    CurrencyExchange getCurrencyExchange(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo);

    default CurrencyExchange getCurrencyExchangeFallBack(String currencyFrom, String currencyTo, Throwable throwable) {
        log.info("Returning dummy response");
        return CurrencyExchange.builder().build();
    }
}
