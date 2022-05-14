package com.amsidh.mvc.currencyexchange.controller;

import com.amsidh.mvc.model.CurrencyExchange;
import com.amsidh.mvc.model.InstanceInfo;
import com.amsidh.mvc.currencyexchange.repository.ExchangeRepository;
import com.amsidh.mvc.currencyexchange.util.MapperUtil;
import com.amsidh.mvc.currencyexchange.entity.CurrencyExchangeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;

    @GetMapping(value = "/health", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String healthCheck() {
        return "{\"status\":\"Currency-Exchange Service is up and running\"}";
    }


    //http://localjost:{RandomPortNumber}/currency-exchange/USD/to/INR
    @SneakyThrows
    @GetMapping(value = "/{currencyFrom}/to/{currencyTo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CurrencyExchange getCurrencyExchange(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo) {
        CurrencyExchangeEntity currencyExchangeEntity = exchangeRepository.findExchangeByCurrencyFromAndCurrencyTo(currencyFrom, currencyTo);
        CurrencyExchange currencyExchange = MapperUtil.toExchangeDTO(currencyExchangeEntity);
        currencyExchange.setInstanceInfo(instanceInfo);
        log.info("Returning response from ExchangeController {}", objectMapper.writeValueAsString(currencyExchange));
        return currencyExchange;
    }
}
