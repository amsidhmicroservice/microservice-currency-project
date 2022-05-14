package com.amsidh.mvc.currencyconversion.feign;

import com.amsidh.mvc.model.CurrencyExchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeClient {
    @RequestMapping(method = RequestMethod.GET, value = "/currency-exchange/{currencyFrom}/to/{currencyTo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    CurrencyExchange getCurrencyExchange(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo);
}
