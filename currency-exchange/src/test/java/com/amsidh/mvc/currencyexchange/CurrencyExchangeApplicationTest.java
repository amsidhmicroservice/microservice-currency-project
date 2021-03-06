package com.amsidh.mvc.currencyexchange;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class CurrencyExchangeApplicationTest {

    @Test
    void contextLoads() {
        log.info("Running contextLoads test method from CurrencyExchangeApplicationTest class");
    }

}

