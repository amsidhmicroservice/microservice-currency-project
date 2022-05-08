package com.amsidh.mvc.repository;

import com.amsidh.mvc.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<CurrencyExchangeEntity, Long>, JpaSpecificationExecutor<CurrencyExchangeEntity> {
    @Query(name = "findExchangeByCurrencyFromAndCurrencyTo", value = "from CurrencyExchangeEntity where currencyFrom=:currencyFrom and currencyTo=:currencyTo")
    CurrencyExchangeEntity findExchangeByCurrencyFromAndCurrencyTo(@Param("currencyFrom") String currencyFrom, @Param("currencyTo") String currencyTo);
}
