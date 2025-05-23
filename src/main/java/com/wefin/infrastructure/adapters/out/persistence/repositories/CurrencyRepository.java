package com.wefin.infrastructure.adapters.out.persistence.repositories;

import com.wefin.infrastructure.adapters.out.persistence.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
    Optional<CurrencyEntity> findByCode(String code);
}

