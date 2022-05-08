package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.client.CurrencyExchangeClient;
import com.amsidh.mvc.model.CurrencyConversion;
import com.amsidh.mvc.model.CurrencyExchange;
import com.amsidh.mvc.service.CurrencyConversionService;
import com.amsidh.mvc.service.InstanceInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final CurrencyExchangeClient currencyExchangeClient;
    private final InstanceInformationService instanceInformationService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public CurrencyConversion getCurrencyConversion(String currencyFrom, String currencyTo, BigDecimal quantity) {
        CurrencyExchange exchange = currencyExchangeClient.getCurrencyExchange(currencyFrom, currencyTo);
        log.info("Response received from currency-exchange service {}", objectMapper.writeValueAsString(exchange));
        CurrencyConversion.CurrencyConversionBuilder builder = CurrencyConversion.builder().from(currencyFrom).to(currencyTo).quantity(quantity).conversionEnvironmentInfo(instanceInformationService.retrieveInstanceInfo());
        builder = exchange != null ? builder.id(exchange.getId()).conversionMultiple(exchange.getConversionMultiple()).exchangeEnvironmentInfo(exchange.getExchangeEnvironmentInfo()).totalCalculatedAmount(quantity.multiply(exchange.getConversionMultiple() != null ? exchange.getConversionMultiple() : new BigDecimal(0))) : builder;
        return builder.build();
    }
}
