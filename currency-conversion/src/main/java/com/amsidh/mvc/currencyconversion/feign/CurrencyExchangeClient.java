package com.amsidh.mvc.currencyconversion.feign;

import com.amsidh.mvc.model.CurrencyExchange;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = "currency-exchange",  url = "${CURRENCY_EXCHANGE_URL:http://localhost:8181}", fallback = CurrencyExchangeClientFallbackFactory.class)
@FeignClient(name = "currency-exchange")
@Retry(name = "currency-exchange-api-call-retry", fallbackMethod = "getCurrencyExchangeFallBack")
@CircuitBreaker(name = "currency-exchange-api-call-circuit-breaker")
public interface CurrencyExchangeClient {
    Logger log = LoggerFactory.getLogger(CurrencyExchangeClient.class);

    @RequestMapping(method = RequestMethod.GET, value = "/currency-exchange/{currencyFrom}/to/{currencyTo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    CurrencyExchange getCurrencyExchange(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo);

    default CurrencyExchange getCurrencyExchangeFallBack(String currencyFrom, String currencyTo, Throwable throwable) {
        log.error("Returning Fallback response for CurrencyExchangeClient due to exception message: {}", throwable.getLocalizedMessage());
        return CurrencyExchange.builder().build();
    }

    ;
}
