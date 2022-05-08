package com.amsidh.mvc.controller;

import com.amsidh.mvc.entity.CurrencyExchangeEntity;
import com.amsidh.mvc.model.CurrencyExchange;
import com.amsidh.mvc.model.InstanceInfo;
import com.amsidh.mvc.repository.ExchangeRepository;
import com.amsidh.mvc.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/currency-exchange")
public class ExchangeController {
    private final ExchangeRepository exchangeRepository;
    private final InstanceInfo instanceInfo;

    @GetMapping(value = "/health", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String healthCheck() {
        return "{\"status\":\"Currency-Exchange Service is up and running\"}";
    }


    //http://34.121.35.177:8181/currency-exchange/USD/to/INR
    @SneakyThrows
    @GetMapping(value = "/{currencyFrom}/to/{currencyTo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CurrencyExchange getCurrencyExchange(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo) {
        log.info("=======Start Request=======");
        log.info("Inside getCurrencyExchange method of ExchangeRepository");
        CurrencyExchangeEntity currencyExchangeEntity = exchangeRepository.findExchangeByCurrencyFromAndCurrencyTo(currencyFrom, currencyTo);
        CurrencyExchange currencyExchange = MapperUtil.toExchangeDTO(currencyExchangeEntity);
        currencyExchange.setInstanceInfo(instanceInfo);
        log.info("=======End Request=======");
        return currencyExchange;
    }
}
