package com.wefin.application.mappers;

import com.wefin.application.dto.ExchangeRateDTO;
import com.wefin.domain.entities.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CurrencyDTOMapper.class})
public interface ExchangeRateDTOMapper {

    ExchangeRate toDomain(ExchangeRateDTO exchangeRateDTO);

    ExchangeRateDTO toDTO(ExchangeRate exchangeRate);
}
