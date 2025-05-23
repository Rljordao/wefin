package com.wefin.application.mappers;

import com.wefin.application.dto.CurrencyDTO;
import com.wefin.domain.entities.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyDTOMapper {

    Currency toDomain(CurrencyDTO currencyDTO);
    CurrencyDTO toDTO(Currency currency);

}

