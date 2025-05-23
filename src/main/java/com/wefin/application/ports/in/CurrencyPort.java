package com.wefin.application.ports.in;

import com.wefin.application.dto.CurrencyDTO;
import com.wefin.application.dto.ExchangeRateDTO;
import com.wefin.application.dto.ExchangeRateUpdateRequest;

public interface CurrencyPort {
    ExchangeRateDTO getCurrentExchangeRate(String sourceCurrencyCode, String targetCurrencyCode);
    ExchangeRateDTO updateExchangeRate(ExchangeRateUpdateRequest request);
    CurrencyDTO getCurrencyByCode(String code);
}