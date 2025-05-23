package com.wefin.infrastructure.adapters.out.persistence.mappers;

import com.wefin.domain.entities.ExchangeRate;
import com.wefin.infrastructure.adapters.out.persistence.entities.ExchangeRateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface ExchangeRateMapper {
    ExchangeRateEntity toEntity(ExchangeRate domain);

    ExchangeRate toDomain(ExchangeRateEntity entity);
}
