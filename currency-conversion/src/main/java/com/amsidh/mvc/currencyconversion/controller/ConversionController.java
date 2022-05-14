package com.amsidh.mvc.currencyconversion.controller;

import com.amsidh.mvc.currencyconversion.service.CurrencyConversionService;
import com.amsidh.mvc.model.CurrencyConversion;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/currency-conversion")
public class ConversionController {

    private final CurrencyConversionService currencyConversionService;
    private final ObjectMapper objectMapper;

    @GetMapping(value = "/health", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String healthCheck() {
        return "{\"status\":\"Currency-Conversion Service is up and running\"}";
    }

    //http://35.222.88.162:8282/currency-conversion/from/USD/to/INR/quantity/100
    @SneakyThrows
    @GetMapping(value = {"/from/{currencyFrom}/to/{currencyTo}/quantity/{quantity}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public CurrencyConversion convertCurrency(@PathVariable("currencyFrom") String currencyFrom, @PathVariable("currencyTo") String currencyTo, @PathVariable("quantity") BigDecimal quantity) {
        CurrencyConversion currencyConversion = currencyConversionService.getCurrencyConversion(currencyFrom, currencyTo, quantity);
        log.info("Returning response currency-conversion service {}", objectMapper.writeValueAsString(currencyConversion));
        return currencyConversion;
    }
}
