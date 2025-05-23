package com.wefin.infrastructure.adapters.out.persistence.adapters;

import com.wefin.application.ports.out.CurrencyRepositoryPort;
import com.wefin.domain.entities.Currency;
import com.wefin.domain.entities.ExchangeRate;
import com.wefin.infrastructure.adapters.out.persistence.entities.ExchangeRateEntity;
import com.wefin.infrastructure.adapters.out.persistence.mappers.CurrencyMapper;
import com.wefin.infrastructure.adapters.out.persistence.mappers.ExchangeRateMapper;
import com.wefin.infrastructure.adapters.out.persistence.repositories.CurrencyRepository;
import com.wefin.infrastructure.adapters.out.persistence.repositories.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyRepositoryAdapter implements CurrencyRepositoryPort {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyMapper currencyMapper;
    private final ExchangeRateMapper exchangeRateMapper;


    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        ExchangeRateEntity entity = exchangeRateMapper.toEntity(exchangeRate);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        entity.setUpdatedAt(LocalDateTime.now());

        ExchangeRateEntity savedEntity = exchangeRateRepository.save(entity);
        return exchangeRateMapper.toDomain(savedEntity);
    }


    @Override
    public Optional<Currency> findCurrencyByCode(String code) {
        return currencyRepository.findByCode(code).map(currencyMapper::toDomain);
    }


    @Override
    public Optional<ExchangeRate> findActiveExchangeRate(Long sourceCurrencyId, Long targetCurrencyId) {
        return exchangeRateRepository
                .findBySourceCurrencyIdAndTargetCurrencyIdAndActiveTrue(sourceCurrencyId, targetCurrencyId)
                .map(exchangeRateMapper::toDomain);
    }

    @Override
    public void deactivateExchangeRate(Long exchangeRateId) {
        exchangeRateRepository.findById(exchangeRateId)
                .ifPresent(entity -> {
                    entity.setActive(false);
                    entity.setUpdatedAt(LocalDateTime.now());
                    exchangeRateRepository.save(entity);
                });
    }
}