package com.wefin.infrastructure.adapters.out.persistence.mappers;

import com.wefin.domain.entities.Currency;
import com.wefin.infrastructure.adapters.out.persistence.entities.CurrencyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyEntity toEntity(Currency domain);
    Currency toDomain(CurrencyEntity entity);

}

