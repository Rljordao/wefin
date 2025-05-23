package com.wefin.infrastructure.adapters.out.persistence.repositories;

import com.wefin.infrastructure.adapters.out.persistence.entities.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
    Optional<ExchangeRateEntity> findBySourceCurrencyIdAndTargetCurrencyIdAndActiveTrue(
            Long sourceCurrencyId, Long targetCurrencyId);
}
