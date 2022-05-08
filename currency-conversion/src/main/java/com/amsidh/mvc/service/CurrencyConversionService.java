package com.amsidh.mvc.service;

import com.amsidh.mvc.model.CurrencyConversion;

import java.math.BigDecimal;

public interface CurrencyConversionService {
    CurrencyConversion getCurrencyConversion(String currencyFrom, String currencyTo, BigDecimal quantity);
}
