package com.amsidh.mvc.feign;

import com.amsidh.mvc.model.CurrencyExchange;
import org.springframework.web.bind.annotation.PathVariable;

public class CurrencyExchangeClientFallback implements CurrencyExchangeClient{
    @Override
    public CurrencyExchange getCurrencyExchange(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo){
        return CurrencyExchange.builder().build();
    }
}
