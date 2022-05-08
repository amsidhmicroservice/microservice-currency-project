package com.amsidh.mvc.util;

import com.amsidh.mvc.entity.CurrencyExchangeEntity;
import com.amsidh.mvc.model.CurrencyExchange;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

public class MapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static CurrencyExchangeEntity toExchange(@NotNull CurrencyExchange currencyExchangeDTO) {
        return modelMapper.map(currencyExchangeDTO, CurrencyExchangeEntity.class);
    }

    public static CurrencyExchange toExchangeDTO(@NotNull CurrencyExchangeEntity currencyExchangeEntity) {
        return modelMapper.map(currencyExchangeEntity, CurrencyExchange.class);
    }

}
