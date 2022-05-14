package com.amsidh.mvc.currencyconversion.feign;

import com.amsidh.mvc.model.CurrencyExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrencyExchangeClientFallbackFactory implements FallbackFactory<CurrencyExchangeClient> {

    @Override
    public CurrencyExchangeClient create(Throwable cause) {
        return new CurrencyExchangeClient() {
            @Override
            public CurrencyExchange getCurrencyExchange(String currencyFrom, String currencyTo) {
                log.info("Fallback CurrencyExchangeClient");
                return CurrencyExchange.builder().build();
            }
        };
    }
}
