package com.wefin.application.services;

import com.wefin.application.dto.CurrencyDTO;
import com.wefin.application.dto.ExchangeRateDTO;
import com.wefin.application.dto.ExchangeRateUpdateRequest;
import com.wefin.application.mappers.CurrencyDTOMapper;
import com.wefin.application.mappers.ExchangeRateDTOMapper;
import com.wefin.application.ports.in.CurrencyPort;
import com.wefin.application.ports.out.CurrencyRepositoryPort;
import com.wefin.domain.entities.Currency;
import com.wefin.domain.entities.ExchangeRate;
import com.wefin.domain.exceptions.BusinessException;
import com.wefin.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CurrencyService implements CurrencyPort {

    private final CurrencyRepositoryPort currencyRepository;
    private final CurrencyDTOMapper currencyMapper;
    private final ExchangeRateDTOMapper exchangeRateMapper;

    @Override
    public ExchangeRateDTO getCurrentExchangeRate(String sourceCurrencyCode, String targetCurrencyCode) {
        Currency sourceCurrency = getCurrencyEntityByCode(sourceCurrencyCode);
        Currency targetCurrency = getCurrencyEntityByCode(targetCurrencyCode);

        ExchangeRate rate = currencyRepository.findActiveExchangeRate(
                        sourceCurrency.getId(), targetCurrency.getId())
                .orElseThrow(() -> new BusinessException(
                        "No active exchange rate found between " + sourceCurrencyCode +
                                " and " + targetCurrencyCode,
                        "NO_ACTIVE_EXCHANGE_RATE"));

        return exchangeRateMapper.toDTO(rate);
    }

    @Override
    @Transactional
    public ExchangeRateDTO updateExchangeRate(ExchangeRateUpdateRequest request) {
        Currency sourceCurrency = getCurrencyEntityByCode(request.getSourceCurrencyCode());
        Currency targetCurrency = getCurrencyEntityByCode(request.getTargetCurrencyCode());

        currencyRepository.findActiveExchangeRate(sourceCurrency.getId(), targetCurrency.getId())
                .ifPresent(rate -> currencyRepository.deactivateExchangeRate(rate.getId()));

        ExchangeRate newRate = ExchangeRate.builder()
                .sourceCurrency(sourceCurrency)
                .targetCurrency(targetCurrency)
                .rate(request.getRate())
                .effectiveDate(request.getEffectiveDate() != null ?
                        request.getEffectiveDate() : LocalDateTime.now())
                .active(true)
                .build();

        ExchangeRate savedRate = currencyRepository.save(newRate);
        return exchangeRateMapper.toDTO(savedRate);
    }


    @Override
    public CurrencyDTO getCurrencyByCode(String code) {
        return currencyMapper.toDTO(getCurrencyEntityByCode(code));
    }

    private Currency getCurrencyEntityByCode(String code) {
        return currencyRepository.findCurrencyByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Currency", code));
    }
}