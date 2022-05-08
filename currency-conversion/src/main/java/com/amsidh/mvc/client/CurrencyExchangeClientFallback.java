package com.amsidh.mvc.client;

import com.amsidh.mvc.model.CurrencyExchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class CurrencyExchangeClientFallback implements CurrencyExchangeClient{
    @Override
    public CurrencyExchange getCurrencyExchange(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo){
        return CurrencyExchange.builder().build();
    }
}
