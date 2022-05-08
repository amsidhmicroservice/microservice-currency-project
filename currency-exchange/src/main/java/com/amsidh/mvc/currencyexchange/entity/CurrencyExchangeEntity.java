package com.amsidh.mvc.currencyexchange.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Exchange")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchangeEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String currencyFrom;

    @Column(nullable = false)
    private String currencyTo;

    @Column(nullable = false)
    private BigDecimal conversionMultiple;
}
