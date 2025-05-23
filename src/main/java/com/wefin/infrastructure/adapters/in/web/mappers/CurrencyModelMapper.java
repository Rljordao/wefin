package com.wefin.infrastructure.adapters.in.web.mappers;

import com.wefin.application.dto.CurrencyDTO;
import com.wefin.openapi.model.CurrencyModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyModelMapper {

    CurrencyModel toModel(CurrencyDTO currencyDTO);

}

