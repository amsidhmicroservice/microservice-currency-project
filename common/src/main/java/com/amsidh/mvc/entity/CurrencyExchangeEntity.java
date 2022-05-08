package com.amsidh.mvc.entity;

import com.amsidh.mvc.model.InstanceInfo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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
