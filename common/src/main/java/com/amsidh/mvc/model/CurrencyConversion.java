package com.amsidh.mvc.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CurrencyConversion {
    private Long id;

    private String from;

    private String to;

    private BigDecimal conversionMultiple;

    private BigDecimal quantity;

    private BigDecimal totalCalculatedAmount;

    private String exchangeEnvironmentInfo;

    private String conversionEnvironmentInfo;
}
