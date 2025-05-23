package com.wefin.infrastructure.adapters.in.web.mappers;

import com.wefin.application.dto.MoneyDTO;
import com.wefin.openapi.model.MoneyModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CurrencyModelMapper.class})
public interface MoneyModelMapper {

    MoneyDTO toDTO(MoneyModel money);
    MoneyModel toModel(MoneyDTO money);

}
