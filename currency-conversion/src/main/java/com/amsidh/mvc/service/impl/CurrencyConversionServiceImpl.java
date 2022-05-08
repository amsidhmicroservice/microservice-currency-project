package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.feign.CurrencyExchangeClient;
import com.amsidh.mvc.model.CurrencyConversion;
import com.amsidh.mvc.model.CurrencyExchange;
import com.amsidh.mvc.model.InstanceInfo;
import com.amsidh.mvc.service.CurrencyConversionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.cloud.function.json.GsonMapper;
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


    @SneakyThrows
    @Override
    public CurrencyConversion getCurrencyConversion(String currencyFrom, String currencyTo, BigDecimal quantity) {
        CurrencyConversion currencyConversion = CurrencyConversion.builder().from(currencyFrom).to(currencyTo).quantity(quantity).instanceInfo(instanceInfo).build();

        CurrencyExchange exchange = currencyExchangeClient.getCurrencyExchange(currencyFrom, currencyTo);
        Optional.ofNullable(exchange).ifPresent(currencyExchange -> {
            try {
                log.info("Response received from currency-exchange service {}", objectMapper.writeValueAsString(exchange));
                currencyConversion.setId(exchange.getId());
                currencyConversion.setConversionMultiple(exchange.getConversionMultiple());
                currencyConversion.setTotalCalculatedAmount(quantity.multiply(exchange.getConversionMultiple() != null ? exchange.getConversionMultiple() : new BigDecimal(0)));
                currencyConversion.getInstanceInfo().setInstanceInfo(exchange.getInstanceInfo());
            } catch (JsonProcessingException e) {
                log.error("Error occurred while parsing CurrencyExchange", e);
            }

        });
        return currencyConversion;
    }
}
