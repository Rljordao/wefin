package com.wefin.application.ports.out;

import com.wefin.domain.entities.Currency;
import com.wefin.domain.entities.ExchangeRate;

import java.util.Optional;

public interface CurrencyRepositoryPort {

    ExchangeRate save(ExchangeRate exchangeRate);
    Optional<Currency> findCurrencyByCode(String code);
    Optional<ExchangeRate> findActiveExchangeRate(Long sourceCurrencyId, Long targetCurrencyId);
    void deactivateExchangeRate(Long exchangeRateId);
}